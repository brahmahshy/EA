package com.easyacg.storage.entity.properties;

import com.easyacg.storage.model.StorageModeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 存储策略配置项
 *
 * @author brahma
 */
@Data
public abstract class StorageProperties {
    /**
     * 存储策略类型
     */
    @NotNull
    private StorageModeEnum type;

    public void init() {}
}
