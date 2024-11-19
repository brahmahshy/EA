package com.acg.easy.photo.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.acg.easy.common.config.StorageProperties;
import com.acg.easy.common.util.FileUtil;
import com.acg.easy.photo.entity.output.PhotoVo;
import com.acg.easy.photo.mapper.PhotoMapper;
import com.acg.easy.photo.service.PhotoService;
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
        List<File> files = getPhotos();
        // todo 直接从本地磁盘读取，后续需要优化为使用 NAS 或 S3 进行存储
        // todo 后续需要优化为多线程执行，或者 批量插入
        // todo 使用多线程需要注意 事务安全问题
        if (CollectionUtil.isEmpty(files)) {
            return null;
        }

        return files.stream().map(PhotoVo::transfer).toList();
    }

    private List<File> getPhotos() {
        List<File> files = switch (storageProperties.getMode()) {
            case SMB -> FileUtil.getSmbFiles(storageProperties.getSmb());
            default -> FileUtil.getFile(storageProperties.getLocal());
        };
        return FileUtil.filterImageFiles(files);
    }
}
