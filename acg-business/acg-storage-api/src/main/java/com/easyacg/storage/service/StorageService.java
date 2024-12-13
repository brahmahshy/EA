package com.easyacg.storage.service;

import com.easyacg.storage.entity.input.CreateStorageBo;
import com.easyacg.storage.entity.input.UpdateStorageBo;
import com.easyacg.storage.entity.output.StorageVo;
import com.easyacg.storage.model.Storage;

import java.util.List;

/**
 * 存储策略服务 —— 数据交互接口
 *
 * @author brahma
 */
public interface StorageService {
    /**
     * 创建存储策略
     *
     * @param storageBo 存储策略Bo
     */
    void createStorage(CreateStorageBo storageBo);

    /**
     * 根据名称删除存储类型
     *
     * @param name 存储类型名称
     */
    void deleteByName(String name);

    /**
     * 更新存储策略
     *
     * @param storageBo 存储策略Bo
     */
    void updateStorage(UpdateStorageBo storageBo);

    /**
     * 根据id获取存储类型
     *
     * @param id 存储类型id
     * @return 存储类型
     */
    Storage getStorageById(String id);

    /**
     * 根据名称获取存储类型
     *
     * @param name 存储类型名称
     * @return 存储类型
     */
    Storage getStorageByName(String name);

    /**
     * 获取所有存储类型
     *
     * @return 存储类型列表
     */
    List<StorageVo> getAllStorage();
}
