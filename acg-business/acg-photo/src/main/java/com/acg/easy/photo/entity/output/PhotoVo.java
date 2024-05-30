package com.acg.easy.photo.entity.output;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.UUID;

@Getter
@Setter
public class PhotoVo {
    private String id;
    private String title;
    private String name;
    private String img_num;
    private String img_src;
    private String guid;

    public static PhotoVo transfer(File file) {
        PhotoVo photoVo = new PhotoVo();
        photoVo.setId(UUID.randomUUID().toString());
        photoVo.setTitle(file.getPath());
        photoVo.setName(file.getName());
        photoVo.setImg_num("1");
        photoVo.setImg_src(file.getAbsolutePath());
        photoVo.setGuid(UUID.randomUUID().toString());
        return photoVo;
    }
}
