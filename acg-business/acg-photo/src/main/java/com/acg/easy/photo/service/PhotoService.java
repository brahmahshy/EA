package com.acg.easy.photo.service;

import com.acg.easy.photo.entity.input.MigrateInput;
import com.acg.easy.photo.entity.output.PhotoVo;

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
     * @param input 入参
     */
    void migratePhotos(MigrateInput input);
}
