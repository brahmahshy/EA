package com.easyacg.storage.entity.input.storage;

import com.easyacg.storage.entity.properties.StorageProperties;
import com.easyacg.storage.entity.properties.StoragePropertiesDeserializer;
import com.easyacg.storage.model.Storage;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 更新存储策略Bo
 *
 * @author brahma
 */
@Data
public class UpdateStorageBo {
    @NotEmpty
    @Digits(integer = 19, fraction = 0)
    private String id;

    private String name;

    @JsonDeserialize(using = StoragePropertiesDeserializer.class)
    private StorageProperties properties;

    private String remark;

    /**
     * 转换为Storage实体
     *
     * @return Storage实体
     */
    public Storage trans() {
        Storage storage = new Storage();
        storage.setId(Long.parseLong(id));

        if (StringUtils.isNotBlank(name)) {
            storage.setName(name);
        }

        if (properties != null) {
            storage.setProperties(properties);
        }

        if (StringUtils.isNotBlank(remark)) {
            storage.setRemark(remark);
        }

        return storage;
    }
}
