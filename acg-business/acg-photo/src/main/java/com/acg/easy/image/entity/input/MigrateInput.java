package com.acg.easy.image.entity.input;

import jakarta.validation.Valid;
import lombok.Data;

/**
 * 用于迁移数据入参
 *
 * @author Administrator
 */
@Data
@Valid
public class MigrateInput {
    private UploadInput fromUpload;

    private UploadInput toUpload;

    private String isCompress;
}
