package com.acg.easy.photo.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.acg.easy.photo.entity.output.PhotoVo;
import com.acg.easy.photo.mapper.PhotoMapper;
import com.acg.easy.photo.service.PhotoService;
import com.acg.easy.storage.FileUtil;
import com.acg.easy.storage.StorageModeEnum;
import com.acg.easy.storage.StorageProperties;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

/**
 * @author Brahma
 */
@Data
@Slf4j
@Service
public class PhotoServiceImpl implements PhotoService {
    @Resource
    private PhotoMapper photoMapper;

    @Resource
    private StorageProperties storageProperties;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PhotoVo> readPhoto() {
        List<File> files = switch (storageProperties.getMode()) {
            case SMB -> FileUtil.getSmbFiles(storageProperties.getSmb());
            default -> FileUtil.getFile(storageProperties.getLocal());
        };
        files = FileUtil.filterImageFiles(files);
        // todo 后续需要优化为多线程执行，或者 批量插入
        // todo 使用多线程需要注意 事务安全问题
        if (CollectionUtil.isEmpty(files)) {
            return null;
        }

        return files.stream().map(PhotoVo::transfer).toList();
    }

    @Override
    public void migratePhotos(StorageModeEnum fromStorage, StorageModeEnum toStorage) {

    }
}
