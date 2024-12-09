package com.easyacg.storage.aspect;

import com.easyacg.core.entity.EasyacgException;
import com.easyacg.storage.EasyacgS3Client;
import com.easyacg.storage.S3Util;
import com.easyacg.storage.properties.S3Properties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.S3Exception;

/**
 * S3工具类切面 - 用于确保S3Client的初始化
 *
 * @author your-name
 */
@Slf4j
@Aspect
@Component
public class S3UtilAspect {

    /**
     * 定义切点 - 拦截S3Util的所有公共方法
     */
    @Pointcut("execution(public * com.easyacg.storage.S3Util.*(..))")
    public void s3Operations() {
    }

    /**
     * 在S3Util方法执行前确保S3Client已初始化
     */
    @Before("s3Operations()")
    public void ensureS3ClientInitialized() {
        try {
            if (S3Util.s3Client == null) {
                log.debug("S3Client未初始化，正在进行初始化...");
                S3Util.s3Client = EasyacgS3Client.build(new S3Properties());
                log.debug("S3Client初始化完成");
            }
        } catch (Exception e) {
            log.error("S3Client初始化失败: {}", e.getMessage(), e);
            throw EasyacgException.build("S3Client初始化失败", e);
        }
    }

    /**
     * 处理S3Exception异常
     */
    @AfterThrowing(pointcut = "s3Operations()", throwing = "ex")
    public void handleS3Exception(JoinPoint joinPoint, S3Exception ex) {
        log.error("S3操作异常: {} - 方法: {}", ex.awsErrorDetails().errorMessage(), joinPoint.getSignature().getName());

        // 抛出包装后的异常
        throw EasyacgException.build("S3操作失败", ex);
    }

    /**
     * 处理其他所有异常
     */
    @AfterThrowing(pointcut = "s3Operations()", throwing = "ex")
    public void handleGeneralException(JoinPoint joinPoint, Exception ex) {
        // 只处理非S3Exception的异常
        if (!(ex instanceof S3Exception)) {
            log.error("S3操作发生未知异常: {} - 方法: {}", ex.getMessage(), joinPoint.getSignature().getName());

            throw EasyacgException.build("S3操作发生未知错误", ex);
        }
    }
} 