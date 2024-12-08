package com.easyacg.storage.properties;

import lombok.Data;

/**
 * S3存储策略配置
 *
 * @author brahma
 */
@Data
public class S3Properties implements StorageProperties {
    private String endpoint;

    private String accessKey;

    private String secretKey;

    private String bucketName;
}
