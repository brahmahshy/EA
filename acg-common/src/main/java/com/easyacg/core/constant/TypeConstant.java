package com.easyacg.core.constant;

/**
 * 对象软类型常量
 *
 * @author brahma
 */
public interface TypeConstant {
    interface Image {
        String JPEG = "com.easyacg.image.jpeg";

        String WEBP = "com.easyacg.image.webp";
    }

    interface Storage {
        String S3 = "com.easyacg.storage.s3";

        String R2 = "com.easyacg.storage.r2";
    }
}
