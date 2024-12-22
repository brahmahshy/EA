package com.easyacg.core.mybatis;

import jakarta.servlet.http.HttpServletRequest;
import org.dromara.mpe.annotation.handler.AutoFillHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Field;

/**
 * 全局获取用户ID
 *
 * @author brahma
 */
@Component
public class UserIdAutoFillHandler implements AutoFillHandler<String> {
    /**
     * @param object 当前操作的数据对象
     * @param clazz  当前操作的数据对象的class
     * @param field  当前操作的数据对象上的字段
     * @return 用户id
     */
    @Override
    public String getVal(Object object, Class<?> clazz, Field field) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 配合网关或者过滤器，token校验成功后就把用户信息塞到header中
        return request.getHeader("user-id");
    }
}