package com.easyacg.storage.entity.input;

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
    private StorageModeEnum mode;
    
    private String disposition;

    private String remark;
}
