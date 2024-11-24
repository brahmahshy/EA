package com.acg.easy.image.service;

import com.acg.easy.image.entity.input.MigrateInput;
import com.acg.easy.image.entity.input.UploadInput;
import com.acg.easy.image.entity.output.ImageVo;
import org.springframework.web.multipart.MultipartFile;

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
     * 上传图像
     *
     * @param file  文件
     * @param input 入参
     */
    void uploadImage(MultipartFile file, UploadInput input);

    /**
     * 将图片从 {@code fromStorage} 迁移至 {@code toStorage}
     *
     * @param input 入参
     */
    void migrateImages(MigrateInput input);
}
