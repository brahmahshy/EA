package com.brahma.controller;

import com.brahma.entity.ResponseVo;
import com.brahma.util.FileUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/photo")
public class PhotoController {
    @Value("${brahma.photo.path:C:\\\\Users\\\\Administrator\\\\Pictures}")
    private String photoPath;

    @GetMapping("get")
    public ResponseVo<String> readPhoto() {
        List<File> file = FileUtil.getFile(photoPath);
        System.out.println(file);
        return ResponseVo.success("查詢成功");
    }
}
