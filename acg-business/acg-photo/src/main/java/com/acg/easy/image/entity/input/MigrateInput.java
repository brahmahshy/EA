package com.acg.easy.image.entity.input;

import com.acg.easy.storage.StorageModeEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 用于迁移数据入参
 *
 * @author Administrator
 */
@Valid
@Getter
@Setter
public class MigrateInput {
    @NotBlank
    private String fromStorage;

    @NotBlank
    private String toStorage;

    private String isCompress;

    public StorageModeEnum getFromStorage() {
        return Enum.valueOf(StorageModeEnum.class, this.fromStorage);
    }

    public StorageModeEnum getToStorage() {
        return Enum.valueOf(StorageModeEnum.class, this.toStorage);
    }
}
