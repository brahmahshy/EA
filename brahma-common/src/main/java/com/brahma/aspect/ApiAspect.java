package com.brahma.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import com.brahma.holder.RequestHolder;

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
    @Around("execution(* com.brahma.controller..*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = RequestHolder.getRequest();
        log.debug(request.getRequestURI());
        return joinPoint.proceed();
    }
}
