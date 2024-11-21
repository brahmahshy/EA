package com.acg.easy.photo.service;

import com.acg.easy.photo.entity.output.PhotoVo;
import com.acg.easy.storage.StorageModeEnum;

import java.util.List;

/**
 * @author braham
 */
public interface PhotoService {
    /**
     * 根据配置项，读取现有的所有照片
     *
     * @return 照片信息
     */
    List<PhotoVo> readPhoto();

    /**
     * 将图片从 {@code fromStorage} 迁移至 {@code toStorage}
     *
     * @param fromStorage 从xxx存储策略迁出
     * @param toStorage   迁入到xxx存储策略
     */
    void migratePhotos(StorageModeEnum fromStorage, StorageModeEnum toStorage);
}
