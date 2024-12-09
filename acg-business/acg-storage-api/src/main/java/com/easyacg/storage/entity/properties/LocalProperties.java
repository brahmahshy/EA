package com.easyacg.storage.entity.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 本地存储策略配置项
 *
 * @author brahma
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LocalProperties extends StorageProperties {
    private String path;
}
