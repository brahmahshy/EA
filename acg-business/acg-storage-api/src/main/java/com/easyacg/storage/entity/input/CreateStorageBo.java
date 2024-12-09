package com.easyacg.storage.entity.input;

import com.easyacg.storage.entity.properties.StorageProperties;
import com.easyacg.storage.entity.properties.StoragePropertiesDeserializer;
import com.easyacg.storage.model.Storage;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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

    @NotNull
    @JsonDeserialize(using = StoragePropertiesDeserializer.class)
    private StorageProperties properties;

    private String remark;

    public Storage trans() {
        Storage storage = new Storage();
        storage.setName(this.getName());
        storage.setMode(properties.getType());
        storage.setProperties(properties);
        storage.setRemark(this.getRemark());
        return storage;
    }
}
