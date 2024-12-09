package com.easyacg.storage.entity.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * S3存储策略配置
 *
 * @author brahma
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class S3Properties extends StorageProperties {
    private String endpoint;

    private String accessKey;

    private String secretKey;

    private String bucketName;
}
