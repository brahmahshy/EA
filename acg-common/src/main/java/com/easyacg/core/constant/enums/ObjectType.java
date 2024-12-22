package com.easyacg.core.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.easyacg.core.constant.TypeConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ObjectType {
    JPEG(TypeConstant.Image.JPEG),

    WEBP(TypeConstant.Image.WEBP),

    S3(TypeConstant.Storage.S3),

    R2(TypeConstant.Storage.R2);

    @EnumValue
    private final String type;
}
