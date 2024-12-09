package com.easyacg.storage.entity.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SMB存储策略配置项
 *
 * @author brahma
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SmbProperties extends StorageProperties {
    private String host;
    private String shareName;
    private String username;
    private String password;
    private String basePath;
}