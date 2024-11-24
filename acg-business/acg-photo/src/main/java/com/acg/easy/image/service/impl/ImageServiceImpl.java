package com.acg.easy.image.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.acg.easy.core.entity.EasyacgException;
import com.acg.easy.image.ImageFormatEnum;
import com.acg.easy.image.entity.input.MigrateInput;
import com.acg.easy.image.entity.input.UploadInput;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author brahma
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
        StorageService<Object> service = StorageFactory.getService(storageMode);
        List<Object> files = service.listObjects();

        // files = filterImageFiles(files);
        if (CollectionUtil.isEmpty(files)) {
            return null;
        }

        return files.stream().map(ImageVo::transfer).toList();
    }

    @Override
    public void uploadImage(MultipartFile file, UploadInput input) {
        // 1. 文件校验
        if (file.isEmpty()) {
            throw EasyacgException.build("上传文件为空");
        }

        // 2. 获取文件信息
        String originalFilename = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);
        long size = file.getSize();

        // 3. 校验文件类型
        if (!ImageFormatEnum.isImage(fileExtension)) {
            throw EasyacgException.build("不支持的文件类型");
        }
        
        try {
            // 4. 生成新的文件名（避免文件名冲突）
            String newFileName = generateUniqueFileName(originalFilename);

            // 5. 构建保存路径
            Path uploadPath = Paths.get(input.getPath()).toAbsolutePath().normalize();

            // 6. 确保目录存在
            Files.createDirectories(uploadPath);

            // 7. 构建完整的文件路径
            Path targetLocation = uploadPath.resolve(newFileName);

            // 8. 保存文件
            StorageService<Object> service = StorageFactory.getService(input.getMode());
            service.putObject(file.getInputStream(), targetLocation);
        } catch (IOException e) {
            throw EasyacgException.build("UploadImage failed!!!", e);
        }
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1).toLowerCase();
    }

    /**
     * 生成唯一文件名
     */
    private String generateUniqueFileName(String originalFilename) {
        String extension = getFileExtension(originalFilename);
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String random = UUID.randomUUID().toString().substring(0, 8);
        return String.format("%s_%s.%s", timestamp, random, extension);
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
