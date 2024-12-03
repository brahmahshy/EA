package com.easyacg.core.holder;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@UtilityClass
public class RequestHolder {
    private final ThreadLocal<RequestAttributes> REQUEST_ATTRIBUTES_THREAD_LOCAL = new ThreadLocal<>();

    private final ThreadLocal<HttpServletRequest> REQUEST_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 获取request
     */
    public void setRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(requestAttributes, true);
        if (requestAttributes instanceof ServletRequestAttributes attributes) {
            REQUEST_THREAD_LOCAL.set(attributes.getRequest());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest servletRequest = REQUEST_THREAD_LOCAL.get();
        if (null == servletRequest) {
            setRequest();
            return REQUEST_THREAD_LOCAL.get();
        } else {
            try {
                servletRequest.getRequestId();
            } catch (IllegalStateException e) {
                setRequest();
                return REQUEST_THREAD_LOCAL.get();
            }
        }
        return servletRequest;
    }
}
