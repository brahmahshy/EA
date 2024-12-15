package com.easyacg.storage.aspect;

import com.easyacg.core.entity.EasyacgException;
import com.easyacg.storage.EaS3Client;
import com.easyacg.storage.S3ClientFactory;
import com.easyacg.storage.model.Storage;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * S3客户端切面
 * 用于处理 {@link InitS3Client} 注解，自动初始化S3客户端
 *
 * @author brahma
 */
@Slf4j
@Aspect
@Order(1)
@Component
public class S3ClientAspect {
    private final ExpressionParser parser = new SpelExpressionParser();

    @Around("@annotation(com.easyacg.storage.aspect.InitS3Client)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        try {
            // 获取方法签名
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();

            // 获取注解
            InitS3Client annotation = method.getAnnotation(InitS3Client.class);
            String spelExpression = annotation.storageSpEL();

            // 创建 SpEL 上下文
            StandardEvaluationContext context = new StandardEvaluationContext();

            // 设置参数
            Object[] args = point.getArgs();
            String[] parameterNames = signature.getParameterNames();
            for (int i = 0; i < args.length; i++) {
                context.setVariable("p" + i, args[i]);
                if (parameterNames != null && i < parameterNames.length) {
                    context.setVariable(parameterNames[i], args[i]);
                }
            }

            // 解析 SpEL 表达式
            Expression expression = parser.parseExpression(spelExpression);
            Object value = expression.getValue(context);

            if (!(value instanceof Storage storage)) {
                throw EasyacgException.build("无法获取Storage对象，SpEL表达式：{}", spelExpression);
            }

            // 初始化S3客户端
            S3ClientFactory.buildAndSetS3Client(storage);
            log.debug("S3客户端初始化成功");

            // 执行目标方法
            return point.proceed();
        } finally {
            // 清理ThreadLocal
            EaS3Client.S3_CLIENT.remove();
            log.debug("S3客户端资源已清理");
        }
    }
}
