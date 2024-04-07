package com.brahma.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;

import com.brahma.entity.ResponseVo;
import com.brahma.service.PhotoService;
import com.brahma.util.FileUtil;

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
    @Value("${brahma.photo.path:C:\\\\Users\\\\Administrator\\\\Pictures}")
    private String photoPath;

    @Resource
    private PhotoService photoService;

    /**
     * 读取照片
     *
     * @return 是否成功
     */
    @GetMapping("get")
    @Operation(description = "根据配置读取照片到服务器")
    public ResponseVo<String> readPhoto() {
        List<File> files = FileUtil.getFile(photoPath);
        System.out.println(files);
        photoService.readPhoto(files);
        return ResponseVo.success("查詢成功");
    }
}
