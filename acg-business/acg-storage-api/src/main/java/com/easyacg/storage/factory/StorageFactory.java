package com.easyacg.storage.factory;

import com.easyacg.core.entity.EasyacgException;
import com.easyacg.storage.model.StorageModeEnum;
import com.easyacg.storage.service.FileService;
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
    private static Map<StorageModeEnum, FileService> storageEnumServiceMap;

    private List<FileService> storageServiceList;

    public static FileService getService(StorageModeEnum storageModeEnum) {
        FileService storageService = storageEnumServiceMap.get(storageModeEnum);
        if (storageService == null) {
            throw EasyacgException.build("{0} 不存在对应的存储策略实现，请核实！！！", storageModeEnum);
        }
        return storageService;
    }

    @Resource
    public void setStorageServiceList(List<FileService> storageServiceList) {
        this.storageServiceList = storageServiceList;
    }

    @PostConstruct
    public void init() {
        storageEnumServiceMap = storageServiceList.stream()
                .collect(Collectors.toMap(FileService::getType, storageService -> storageService));
    }
}
