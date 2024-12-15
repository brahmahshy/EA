package com.easyacg.storage.entity.input.storage;

import com.easyacg.storage.entity.properties.StorageProperties;
import com.easyacg.storage.entity.properties.StoragePropertiesDeserializer;
import com.easyacg.storage.model.Storage;
import com.easyacg.storage.model.StorageModeEnum;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 创建存储策略接口入参
 *
 * @author brahma
 */
@Data
public class CreateStorageBo {
    @NotBlank
    private String name;

    @Valid
    @NotNull
    @JsonDeserialize(using = StoragePropertiesDeserializer.class)
    private StorageProperties properties;

    private String remark;

    public Storage trans() {
        Storage storage = new Storage();
        storage.setName(this.name);
        storage.setMode(StorageModeEnum.valueOf(this.properties.getType()));
        storage.setProperties(this.properties);
        storage.setRemark(this.remark);
        return storage;
    }
}
