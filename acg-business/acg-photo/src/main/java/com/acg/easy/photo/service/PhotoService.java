package com.acg.easy.photo.service;

import com.acg.easy.photo.entity.output.PhotoVo;

import java.io.File;
import java.util.List;

public interface PhotoService {
    List<PhotoVo> readPhoto(List<File> files);
}
