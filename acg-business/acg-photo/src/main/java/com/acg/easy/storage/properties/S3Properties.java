package com.acg.easy.storage.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * S3存储策略配置
 *
 * @author brahma
 */

@Data
@Component
@ConfigurationProperties(prefix = "easyacg.storage.s3")
public class S3Properties {
    private String endpoint;

    private String accessKey;

    private String secretKey;

    private String bucketName;
}
