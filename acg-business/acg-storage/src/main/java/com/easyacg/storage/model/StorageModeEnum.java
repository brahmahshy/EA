package com.easyacg.storage.model;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 存储策略模式<br/>
 * 通过配置不同的存储策略，进行存储策略的连接
 *
 * @author brahma
 */
@Getter
@AllArgsConstructor
public enum StorageModeEnum {
    LOCAL(0, "本地存储"),

    SMB(1, "SMB存储"),

    S3(10, "S3存储"),

    R2(11, "R2存储");

    /**
     * 存储策略模式
     */
    @EnumValue
    private final int code;

    /**
     * 描述
     */
    private final String description;
}