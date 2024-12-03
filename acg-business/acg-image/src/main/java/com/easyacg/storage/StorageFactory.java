package com.easyacg.storage;

import com.easyacg.core.entity.EasyacgException;
import com.easyacg.storage.mybatis.enums.StorageModeEnum;
import com.easyacg.storage.service.StorageBusinessService;
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
    private static Map<StorageModeEnum, StorageBusinessService> storageEnumServiceMap;

    @Resource
    private List<StorageBusinessService> storageServiceList;

    public static StorageBusinessService getService(StorageModeEnum storageModeEnum) {
        StorageBusinessService storageBusinessService = storageEnumServiceMap.get(storageModeEnum);
        if (storageBusinessService == null) {
            throw EasyacgException.build("{0} 不存在对应的存储策略实现，请核实！！！", storageModeEnum);
        }
        return storageBusinessService;
    }

    @PostConstruct
    public void init() {
        storageEnumServiceMap = storageServiceList.stream()
                .collect(Collectors.toMap(StorageBusinessService::getStorage, storageService -> storageService));
    }
}
