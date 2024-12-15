package com.easyacg.storage.aspect;

import java.lang.annotation.*;

/**
 * 初始化S3客户端注解，主要用于 {@link S3ClientAspect} 解析
 *
 * @author brahma
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InitS3Client {
    /**
     * Spring Expression Language (SpEL) 表达式，用于获取 Storage 对象
     * 默认从第一个参数中获取 storage 属性
     */
    String storageSpEL() default "#p0.storage";
}
