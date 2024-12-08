package com.easyacg.storage.properties;

import lombok.Data;

/**
 * SMB存储策略配置项
 *
 * @author brahma
 */
@Data
public class SmbProperties implements StorageProperties {
    private String host;
    private String shareName;
    private String username;
    private String password;
    private String basePath;
}