package com.acg.easy.photo.entity.output;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.UUID;

/**
 * @author brahma
 */
@Getter
@Setter
public class PhotoVo {
    private String id;
    private String title;
    private String name;
    private String imgNum;
    private String imgSrc;
    private String guid;

    public static PhotoVo transfer(File file) {
        PhotoVo photoVo = new PhotoVo();
        photoVo.setId(UUID.randomUUID().toString());
        photoVo.setTitle(file.getPath());
        photoVo.setName(file.getName());
        photoVo.setImgNum("1");
        photoVo.setImgSrc(file.getAbsolutePath());
        photoVo.setGuid(UUID.randomUUID().toString());
        return photoVo;
    }
}
