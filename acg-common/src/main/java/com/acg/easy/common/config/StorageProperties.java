package com.acg.easy.common.config;

import com.acg.easy.common.enums.StorageMode;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Brahma
 */
@Data
@Component
@ConfigurationProperties(prefix = "easyacg.storage")
public class StorageProperties {
    private StorageMode mode = StorageMode.LOCAL;
    private LocalProperties local;
    private SmbProperties smb;

    @Data
    public static class LocalProperties {
        private String path;
    }

    @Data
    public static class SmbProperties {
        private String host;
        private String shareName;
        private String username;
        private String password;
        private String basePath;
    }
} 