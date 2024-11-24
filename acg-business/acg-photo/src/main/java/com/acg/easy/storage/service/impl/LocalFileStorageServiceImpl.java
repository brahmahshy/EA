package com.acg.easy.storage.service.impl;

import com.acg.easy.core.entity.EasyacgException;
import com.acg.easy.storage.StorageModeEnum;
import com.acg.easy.storage.properties.LocalProperties;
import com.acg.easy.storage.service.StorageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 本地文件存储策略
 *
 * @author brahma
 */
@Slf4j
@Service
public class LocalFileStorageServiceImpl implements StorageService<File> {
    @Resource
    private LocalProperties localProperties;

    @Override
    public StorageModeEnum getStorage() {
        return StorageModeEnum.LOCAL;
    }

    @Override
    public List<File> listObjects() {
        String path = localProperties.getPath();
        File file = new File(path);
        return getFile(file);
    }

    private List<File> getFile(File file) {
        if (!file.exists()) {
            throw EasyacgException.build("文件或文件夹不存在！！！");
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

    @Override
    public void putObject(InputStream inputStream, Path path) throws IOException {
        FileCopyUtils.copy(inputStream, Files.newOutputStream(path));
    }
}
