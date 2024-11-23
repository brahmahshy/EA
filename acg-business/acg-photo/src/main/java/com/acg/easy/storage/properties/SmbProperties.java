package com.acg.easy.storage.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * SMB存储策略配置项
 *
 * @author brahma
 */
@Data
@Component
@ConfigurationProperties(prefix = "easyacg.storage.smb")
public class SmbProperties {
    private String host;
    private String shareName;
    private String username;
    private String password;
    private String basePath;
}