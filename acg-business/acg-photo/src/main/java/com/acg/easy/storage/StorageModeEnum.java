package com.acg.easy.storage;

/**
 * 存储策略模式<br/>
 * 通过配置不同的存储策略，进行存储策略的连接
 *
 * @author brahma
 */

public enum StorageModeEnum {
    LOCAL,
    SMB,
    S3
} 