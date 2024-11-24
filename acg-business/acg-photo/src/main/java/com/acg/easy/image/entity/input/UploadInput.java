package com.acg.easy.image.entity.input;

import com.acg.easy.storage.StorageModeEnum;
import lombok.Data;

/**
 * 上传入参
 *
 * @author brahma
 */
@Data
public class UploadInput {
    private String mode;

    private String path;

    public StorageModeEnum getMode() {
        return Enum.valueOf(StorageModeEnum.class, this.mode);
    }
}
