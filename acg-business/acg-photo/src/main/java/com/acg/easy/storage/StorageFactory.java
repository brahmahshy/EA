package com.acg.easy.storage;

import com.acg.easy.core.entity.EasyacgException;
import com.acg.easy.storage.service.StorageService;
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
    private static Map<StorageModeEnum, StorageService<Object>> storageEnumServiceMap;

    @Resource
    private List<StorageService<Object>> storageServiceList;

    @PostConstruct
    public void init() {
        storageEnumServiceMap = storageServiceList.stream()
                .collect(Collectors.toMap(StorageService::getStorage, storageService -> storageService));
    }

    public static StorageService<Object> getService(StorageModeEnum storageModeEnum) {
        StorageService<Object> storageService = storageEnumServiceMap.get(storageModeEnum);
        if (storageService == null) {
            throw EasyacgException.build("{0} 不存在对应的存储策略实现，请核实！！！", storageModeEnum);
        }
        return storageService;
    }
}
