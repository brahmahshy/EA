package com.easyacg.storage.entity.properties;

import com.easyacg.core.validate.IsEnum;
import com.easyacg.storage.model.StorageModeEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 存储策略配置项
 *
 * @author brahma
 */
@Data
public class StorageProperties {
    /**
     * 存储策略类型
     */
    @NotBlank
    @IsEnum(enumClass = StorageModeEnum.class)
    private String type;

    public void init() {}
}
