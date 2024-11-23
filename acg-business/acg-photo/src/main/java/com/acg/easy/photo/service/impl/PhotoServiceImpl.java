package com.acg.easy.photo.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.acg.easy.photo.entity.output.PhotoVo;
import com.acg.easy.photo.mapper.PhotoMapper;
import com.acg.easy.photo.service.PhotoService;
import com.acg.easy.storage.ImageFormatEnum;
import com.acg.easy.storage.StorageFactory;
import com.acg.easy.storage.StorageModeEnum;
import com.acg.easy.storage.service.StorageService;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Brahma
 */
@Data
@Slf4j
@Service
public class PhotoServiceImpl implements PhotoService {
    @Value("${easyacg.storage.mode:LOCAL}")
    private StorageModeEnum storageMode;

    @Resource
    private PhotoMapper photoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PhotoVo> readPhoto() {
        StorageService service = StorageFactory.getService(storageMode);
        List<File> files = service.readFileList();

        files = filterImageFiles(files);
        if (CollectionUtil.isEmpty(files)) {
            return null;
        }

        return files.stream().map(PhotoVo::transfer).toList();
    }

    @Override
    public void migratePhotos(StorageModeEnum fromStorage, StorageModeEnum toStorage) {

    }

    /**
     * 过滤出图片文件
     *
     * @param files 文件列表
     * @return 图片文件列表
     */
    private List<File> filterImageFiles(List<File> files) {
        if (files == null || files.isEmpty()) {
            return new ArrayList<>();
        }
        return files.stream().filter(file -> ImageFormatEnum.isImage(file.getName())).collect(Collectors.toList());
    }
}
