package com.acg.easy.image.entity.output;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.UUID;

/**
 * @author brahma
 */
@Getter
@Setter
public class ImageVo {
    private String id;
    private String title;
    private String name;
    private String imgNum;
    private String imgSrc;
    private String guid;

    public static ImageVo transfer(File file) {
        ImageVo imageVo = new ImageVo();
        imageVo.setId(UUID.randomUUID().toString());
        imageVo.setTitle(file.getPath());
        imageVo.setName(file.getName());
        imageVo.setImgNum("1");
        imageVo.setImgSrc(file.getAbsolutePath());
        imageVo.setGuid(UUID.randomUUID().toString());
        return imageVo;
    }
}
