package com.acg.easy.storage.service;

import com.acg.easy.storage.StorageModeEnum;

import java.io.File;
import java.util.List;

/**
 * 存储策略服务
 *
 * @author brahma
 */
public interface StorageService {
    StorageModeEnum getStorage();

    List<File> readFileList();
}
