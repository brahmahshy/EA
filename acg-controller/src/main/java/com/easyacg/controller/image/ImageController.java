package com.easyacg.controller.image;

import com.easyacg.core.entity.ResponseVo;
import com.easyacg.image.entity.input.MigrateInput;
import com.easyacg.image.entity.input.UploadInput;
import com.easyacg.image.entity.output.ImageVo;
import com.easyacg.image.service.ImageBusinessService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 照片服务
 *
 * @author brahma
 */
@Slf4j
@RestController
@RequestMapping("/image")
public class ImageController {
    @Resource
    private ImageBusinessService imageBusinessService;

    /**
     * 读取照片
     *
     * @return 是否成功
     */
    @GetMapping("/read")
    @Operation(description = "根据存储策略名称，读取下面所有的图片")
    public ResponseVo<List<ImageVo>> readImage(@RequestParam String storageName) {
        return ResponseVo.success(imageBusinessService.getImageByStorageName(storageName));
    }

    /**
     * 上传照片
     *
     * @return 是否成功
     */
    @PostMapping(value = "/upload")
    @Operation(description = "将本地图像上传到其他地方")
    public ResponseVo<Void> uploadImage(@RequestPart MultipartFile file, @RequestPart UploadInput input) {
        log.info("开始处理文件迁移请求 - 文件名: {}, 大小: {} bytes", file.getOriginalFilename(), file.getSize());
        imageBusinessService.uploadImage(file, input);
        return ResponseVo.success();
    }

    /**
     * 迁移照片
     *
     * @return 是否成功
     */
    @PostMapping(value = "/migrate")
    @Operation(description = "将图片从A迁移到B")
    public ResponseVo<Void> migrateImages(@RequestBody MigrateInput input) {
        imageBusinessService.migrateImages(input);
        return ResponseVo.success();
    }
}
