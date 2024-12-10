package com.easyacg.image.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.easyacg.core.entity.EasyacgException;
import com.easyacg.image.constant.ImageFormatEnum;
import com.easyacg.image.entity.input.MigrateInput;
import com.easyacg.image.entity.input.UploadInput;
import com.easyacg.image.entity.output.ImageVo;
import com.easyacg.image.model.Image;
import com.easyacg.image.repository.ImageRepository;
import com.easyacg.image.service.ImageService;
import com.easyacg.storage.entity.output.FileInfoVo;
import com.easyacg.storage.factory.StorageFactory;
import com.easyacg.storage.model.Storage;
import com.easyacg.storage.repository.StorageRepository;
import com.easyacg.storage.service.FileService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author brahma
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    @Resource
    private ImageRepository imageRepository;

    @Resource
    private StorageRepository storageRepository;

    @Override
    public List<ImageVo> getImageByStorageName(String storageName) {
        LambdaQueryWrapper<Storage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Storage::getName, storageName);
        Storage storageDo = storageRepository.getOne(wrapper);


        LambdaQueryWrapper<Image> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(Image::getStorageId, storageDo.getId());
        List<Image> images = imageRepository.list(wrapper2);
        if (CollectionUtil.isEmpty(images)) {
            return Collections.emptyList();
        }
        return images.stream().map(ImageVo::transfer).toList();
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
            LambdaQueryWrapper<Storage> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Storage::getName, input.getStorageName());
            Storage storageDo = storageRepository.getOne(wrapper);


            FileService service = StorageFactory.getService(storageDo.getMode());
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
     * @param infoVos 文件列表
     * @return 图片文件列表
     */
    private List<FileInfoVo> filterImageFiles(List<FileInfoVo> infoVos) {
        if (infoVos == null || infoVos.isEmpty()) {
            return new ArrayList<>();
        }
        return infoVos.stream().filter(info -> ImageFormatEnum.isImage(info.getFileType())).toList();
    }
}
