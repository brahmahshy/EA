package com.easyacg.storage.service;

import com.easyacg.storage.entity.properties.S3Properties;

/**
 * 用于标识S3存储的接口
 *
 * @author brahma
 */
public interface S3StorageService {
    /**
     * 获取S3相关配置
     *
     * @return S3配置
     */
    S3Properties getS3Properties();
}
