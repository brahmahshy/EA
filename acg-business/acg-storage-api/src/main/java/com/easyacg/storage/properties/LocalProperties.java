package com.easyacg.storage.properties;

import lombok.Data;

/**
 * 本地存储策略配置项
 *
 * @author brahma
 */
@Data
public class LocalProperties implements StorageProperties {
    private String path;
}
