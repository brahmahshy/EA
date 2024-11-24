package com.acg.easy.controller.image;

import com.acg.easy.core.entity.ResponseVo;
import com.acg.easy.image.entity.input.MigrateInput;
import com.acg.easy.image.entity.output.ImageVo;
import com.acg.easy.image.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 照片服务
 *
 * @author brahma
 */
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
     * 迁移照片
     *
     * @return 是否成功
     */
    @GetMapping("/migrate")
    @Operation(description = "将图片从A迁移到B")
    public ResponseVo<Void> migrateImages(@RequestBody @Validated MigrateInput input) {
        imageService.migrateImages(input);
        return ResponseVo.success();
    }
}
