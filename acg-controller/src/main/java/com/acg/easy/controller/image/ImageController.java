package com.acg.easy.controller.image;

import com.acg.easy.core.entity.ResponseVo;
import com.acg.easy.image.entity.input.MigrateInput;
import com.acg.easy.image.entity.input.UploadInput;
import com.acg.easy.image.entity.output.ImageVo;
import com.acg.easy.image.service.ImageService;
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
    private ImageService imageService;

    /**
     * 读取照片
     *
     * @return 是否成功
     */
    @GetMapping("/read")
    @Operation(description = "根据配置读取照片到服务器")
    public ResponseVo<List<ImageVo>> readImage() {
        return ResponseVo.success(imageService.readImage());
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
        imageService.uploadImage(file, input);
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
        imageService.migrateImages(input);
        return ResponseVo.success();
    }
}
