package com.easyacg.storage.entity.input;

import com.alibaba.fastjson2.JSONObject;
import com.easyacg.storage.model.Storage;
import com.easyacg.storage.model.StorageModeEnum;
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
    private StorageModeEnum mode;

    @NotNull
    private JSONObject properties;

    private String remark;

    public Storage trans() {
        Storage storage = new Storage();
        storage.setName(this.getName());
        storage.setMode(this.mode);
        storage.setProperties(properties.to(mode.getPropertiesClazz()));
        storage.setRemark(this.getRemark());
        return storage;
    }
}
