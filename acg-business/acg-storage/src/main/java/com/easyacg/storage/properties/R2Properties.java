package com.easyacg.storage.properties;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Cloudflare S3 存储策略配置
 *
 * @author brahma
 */
@Data
@Component
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = "easyacg.storage.r2")
public class R2Properties extends S3Properties {
    private String accountId;
}
