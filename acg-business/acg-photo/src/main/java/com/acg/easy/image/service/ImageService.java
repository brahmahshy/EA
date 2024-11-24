package com.acg.easy.image.service;

import com.acg.easy.image.entity.input.MigrateInput;
import com.acg.easy.image.entity.output.ImageVo;

import java.util.List;

/**
 * @author braham
 */
public interface ImageService {
    /**
     * 根据配置项，读取现有的所有照片
     *
     * @return 照片信息
     */
    List<ImageVo> readImage();

    /**
     * 将图片从 {@code fromStorage} 迁移至 {@code toStorage}
     *
     * @param input 入参
     */
    void migrateImages(MigrateInput input);
}
