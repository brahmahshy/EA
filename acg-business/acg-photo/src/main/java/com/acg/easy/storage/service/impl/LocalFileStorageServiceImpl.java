package com.acg.easy.storage.service.impl;

import com.acg.easy.core.entity.BrahmaException;
import com.acg.easy.storage.StorageModeEnum;
import com.acg.easy.storage.properties.LocalProperties;
import com.acg.easy.storage.service.StorageService;
import jakarta.annotation.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 本地文件存储策略
 *
 * @author brahma
 */
public class LocalFileStorageServiceImpl implements StorageService {
    @Resource
    private LocalProperties localProperties;

    @Override
    public StorageModeEnum getStorage() {
        return StorageModeEnum.LOCAL;
    }

    @Override
    public List<File> readFileList() {
        String path = localProperties.getPath();
        File file = new File(path);
        return getFile(file);
    }

    private List<File> getFile(File file) {
        if (!file.exists()) {
            throw new BrahmaException("文件或文件夹不存在！！！");
        }

        List<File> fileList = new ArrayList<>();

        for (File listFile : Objects.requireNonNull(file.listFiles())) {
            if (listFile.isDirectory()) {
                fileList.addAll(getFile(listFile));
                continue;
            }

            fileList.add(listFile);
        }

        return fileList;
    }
}
