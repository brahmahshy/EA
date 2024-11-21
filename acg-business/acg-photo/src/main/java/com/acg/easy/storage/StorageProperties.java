package com.acg.easy.storage;

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
    private StorageModeEnum mode = StorageModeEnum.LOCAL;
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