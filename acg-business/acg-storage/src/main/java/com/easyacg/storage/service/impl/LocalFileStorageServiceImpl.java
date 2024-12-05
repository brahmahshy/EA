package com.easyacg.storage.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import com.easyacg.core.entity.EasyacgException;
import com.easyacg.storage.entity.output.FileInfoVo;
import com.easyacg.storage.model.StorageModeEnum;
import com.easyacg.storage.properties.LocalProperties;
import com.easyacg.storage.service.StorageService;
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
public class LocalFileStorageServiceImpl implements StorageService {
    @Resource
    private LocalProperties localProperties;

    @Override
    public StorageModeEnum getStorage() {
        return StorageModeEnum.LOCAL;
    }

    @Override
    public List<FileInfoVo> listObjects() {
        String basePath = localProperties.getPath();
        File baseDir = new File(basePath);
        List<File> files = getFile(baseDir);

        return files.stream()
                .map(file -> FileInfoVo.builder()
                        .fileName(file.getName())
                        .filePath(basePath)
                        .fileSize(file.length())
                        .lastModified(LocalDateTimeUtil.of(file.lastModified()))
                        .fileType(FileUtil.extName(file))
                        .inputStream(FileUtil.getInputStream(file))
                        .build())
                .toList();
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
