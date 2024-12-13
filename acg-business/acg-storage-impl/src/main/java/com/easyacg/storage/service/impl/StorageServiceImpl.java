package com.easyacg.storage.service.impl;

import com.easyacg.core.entity.EasyacgException;
import com.easyacg.storage.entity.input.CreateStorageBo;
import com.easyacg.storage.entity.input.UpdateStorageBo;
import com.easyacg.storage.entity.output.StorageVo;
import com.easyacg.storage.model.Storage;
import com.easyacg.storage.repository.StorageRepository;
import com.easyacg.storage.service.StorageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 存储策略服务 —— 数据交互实现类
 *
 * @author brahma
 */
@Slf4j
@Service
public class StorageServiceImpl implements StorageService {
    @Resource
    private StorageRepository storageRepository;

    @Override
    public void createStorage(CreateStorageBo storageBo) {
        boolean isCreated = storageRepository.save(storageBo.trans());
        if (!isCreated) {
            log.error("创建存储策略失败，名称：{}", storageBo.getName());
            throw EasyacgException.build("创建 {} 存储策略失败", storageBo.getName());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByName(String name) {
        Storage storage = getStorageByName(name);
        if (storage == null) {
            // 没有找到存储策略，等同于删除成功
            return;
        }
        boolean isDeleted = storageRepository.removeById(storage);
        if (!isDeleted) {
            log.error("删除存储策略失败，名称：{}", name);
            throw EasyacgException.build("删除 {} 存储策略失败", name);
        }
    }

    @Override
    public void updateStorage(UpdateStorageBo storageBo) {
        storageRepository.updateById(storageBo.trans());
    }

    @Override
    public Storage getStorageById(String id) {
        return storageRepository.getById(id);
    }

    @Override
    public Storage getStorageByName(String name) {
        return storageRepository.lambdaQueryPlus().eq(Storage::getName, name).one();
    }

    @Override
    public List<StorageVo> getAllStorage() {
        List<Storage> storageList = storageRepository.list();
        return StorageVo.transList(storageList);
    }
}
