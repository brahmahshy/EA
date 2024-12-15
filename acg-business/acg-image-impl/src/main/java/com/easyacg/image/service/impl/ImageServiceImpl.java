package com.easyacg.image.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import com.easyacg.core.contents.enums.BooleanEnum;
import com.easyacg.core.entity.EasyacgException;
import com.easyacg.image.constant.ImageFormatEnum;
import com.easyacg.image.entity.input.MigrateInput;
import com.easyacg.image.entity.input.ReadByStorageBo;
import com.easyacg.image.entity.input.UploadInput;
import com.easyacg.image.entity.output.ImageVo;
import com.easyacg.image.model.Image;
import com.easyacg.image.repository.ImageRepository;
import com.easyacg.image.service.ImageService;
import com.easyacg.image.util.WebpUtil;
import com.easyacg.storage.entity.input.file.PutObjectBo;
import com.easyacg.storage.entity.output.FileInfoVo;
import com.easyacg.storage.factory.StorageFactory;
import com.easyacg.storage.model.Storage;
import com.easyacg.storage.repository.StorageRepository;
import com.easyacg.storage.service.StorageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.mpe.bind.Binder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author brahma
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    @Value("{ea.image.temp}")
    private String tempPath;

    @Resource
    private ImageRepository imageRepository;

    @Resource
    private StorageService storageService;

    @Resource
    private StorageRepository storageRepository;

    @Override
    public List<ImageVo> readImage(ReadByStorageBo storageBo) {
        Storage storage;
        if (StringUtils.isNotBlank(storageBo.getId())) {
            storage = storageService.getStorageById(storageBo.getId());
        } else {
            storage = storageService.getStorageByName(storageBo.getName());
        }
        return getImagesByStorage(storage);
    }

    private List<ImageVo> getImagesByStorage(Storage storage) {
        if (storage == null) {
            return Collections.emptyList();
        }

        Binder.bindOn(storage, Storage::getImageList);

        List<Image> imageList = storage.getImageList();
        if (CollectionUtil.isEmpty(imageList)) {
            return Collections.emptyList();
        }
        return imageList.stream().map(ImageVo::transfer).collect(Collectors.toList());
    }

    @Override
    public void uploadImage(MultipartFile file, UploadInput input) {
        // 文件校验
        if (file.isEmpty()) {
            throw EasyacgException.build("上传文件为空");
        }

        // 校验文件类型
        String mimeType = FileUtil.getMimeType(file.getOriginalFilename());
        if (!ImageFormatEnum.isImageByMimeType(mimeType)) {
            throw EasyacgException.build("不支持的文件类型");
        }

        try {
            // 构建临时文件并上传
            String fileName = this.buildTempFileAndUpload(file, input);
        } catch (IOException e) {
            throw EasyacgException.build("UploadImage failed!!!", e);
        }
    }


    private String buildTempFileAndUpload(MultipartFile file, UploadInput input) throws IOException {
        Path tempFile = null;
        try {
            if (BooleanEnum.isTrue(input.getIsNeedLossy())) {
                tempFile = WebpUtil.convertJpegToWebpWithLossyCompression(file);
            } else {
                tempFile = Files.createTempFile("", FileUtil.extName(file.getOriginalFilename()));
                file.transferTo(tempFile);
            }

            // 构建保存路径
            Path uploadPath = Paths.get(input.getPath()).normalize();

            // 保存文件
            Storage storage = storageService.getStorageByName(input.getStorageName());
            PutObjectBo putObjectBo =
                    PutObjectBo.builder().storage(storage).file(tempFile.toFile()).path(uploadPath).build();
            return StorageFactory.getService(storage.getMode()).putObject(putObjectBo);
        } finally {
            if (tempFile != null) {
                Files.delete(tempFile);
            }
        }
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
