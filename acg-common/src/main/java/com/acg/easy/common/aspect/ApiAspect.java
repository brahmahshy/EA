package com.acg.easy.common.aspect;

import com.acg.easy.common.holder.RequestHolder;
import com.acg.easy.common.util.IpUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Api调用全局切面处理
 */
@Slf4j
@Aspect
@Component
public class ApiAspect {
    @Around("execution(* com.acg.easy.controller..*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = RequestHolder.getRequest();
        log.info("request ip: {}, url: {}, params: {}", IpUtil.getIp(request), request.getRequestURI(),
                 joinPoint.getArgs());
        return joinPoint.proceed();
    }
}
