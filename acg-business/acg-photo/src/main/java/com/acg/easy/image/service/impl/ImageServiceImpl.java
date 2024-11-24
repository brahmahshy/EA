package com.acg.easy.image.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.acg.easy.image.ImageFormatEnum;
import com.acg.easy.image.entity.input.MigrateInput;
import com.acg.easy.image.entity.output.ImageVo;
import com.acg.easy.image.mapper.ImageMapper;
import com.acg.easy.image.service.ImageService;
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
public class ImageServiceImpl implements ImageService {
    @Value("${easyacg.storage.mode:LOCAL}")
    private StorageModeEnum storageMode;

    @Resource
    private ImageMapper imageMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ImageVo> readImage() {
        StorageService service = StorageFactory.getService(storageMode);
        List<File> files = service.readFileList();

        files = filterImageFiles(files);
        if (CollectionUtil.isEmpty(files)) {
            return null;
        }

        return files.stream().map(ImageVo::transfer).toList();
    }

    @Override
    public void migrateImages(MigrateInput input) {

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
