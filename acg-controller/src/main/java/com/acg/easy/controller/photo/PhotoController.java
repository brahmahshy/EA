package com.acg.easy.controller.photo;

import com.acg.easy.photo.entity.output.PhotoVo;
import com.acg.easy.photo.service.PhotoService;
import com.acg.easy.common.entity.ResponseVo;
import com.acg.easy.common.util.FileUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

/**
 * 照片服务
 */
@RestController
@RequestMapping("/photo")
public class PhotoController {
    @Value("${brahma.photo.path:G:\\活动记录}")
    private String photoPath;

    @Resource
    private PhotoService photoService;

    /**
     * 读取照片
     *
     * @return 是否成功
     */
    @GetMapping("/get")
    @Operation(description = "根据配置读取照片到服务器")
    public ResponseVo<List<PhotoVo>> readPhoto() {
        List<File> files = FileUtil.getFile(photoPath);
        System.out.println(files);
        return ResponseVo.success(photoService.readPhoto(files));
    }
}
