package com.easyacg.storage.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 本地存储策略配置项
 *
 * @author brahma
 */
@Data
@Component
@ConfigurationProperties(prefix = "easyacg.storage.local")
public class LocalProperties {
    private String path;
}
