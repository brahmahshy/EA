package com.easyacg.storage.entity.input;

import com.alibaba.fastjson2.JSONObject;
import com.easyacg.storage.model.Storage;
import com.easyacg.storage.model.StorageModeEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 创建存储策略接口入参
 *
 * @author brahma
 */
@Valid
@Getter
@Setter
public class CreateStorageBo {
    @NotBlank
    private String name;

    @NotNull
    private String mode;

    @NotNull
    private JSONObject properties;

    private String remark;

    public Storage trans() {
        Storage storage = new Storage();
        storage.setName(this.getName());
        StorageModeEnum mode = StorageModeEnum.valueOf(this.mode);
        storage.setMode(mode);
        storage.setProperties(properties.to(mode.getPropertiesClazz()));
        storage.setRemark(this.getRemark());
        return storage;
    }
}
