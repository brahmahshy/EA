package com.acg.easy.controller.photo;

import com.acg.easy.core.entity.ResponseVo;
import com.acg.easy.photo.entity.output.PhotoVo;
import com.acg.easy.photo.service.PhotoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 照片服务
 *
 * @author brahma
 */
@RestController
@RequestMapping("/photo")
public class PhotoController {
    @Resource
    private PhotoService photoService;

    /**
     * 读取照片
     *
     * @return 是否成功
     */
    @GetMapping("/read")
    @Operation(description = "根据配置读取照片到服务器")
    public ResponseVo<List<PhotoVo>> readPhoto() {
        return ResponseVo.success(photoService.readPhoto());
    }
}
