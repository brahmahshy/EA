package com.acg.easy.storage;

import com.acg.easy.photo.service.StorageService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 存储策略工厂类
 *
 * @author brahma
 */
@Component
public class StorageFactory {
    @Resource
    private List<StorageService> storageServiceList;

    private static Map<StorageModeEnum, StorageService> storageEnumServiceMap;

    @PostConstruct
    public void init() {
        storageEnumServiceMap = storageServiceList.stream()
                .collect(Collectors.toMap(StorageService::getStorage, storageService -> storageService));
    }

    public static StorageService getService(StorageModeEnum storageModeEnum) {
        return storageEnumServiceMap.get(storageModeEnum);
    }
}
