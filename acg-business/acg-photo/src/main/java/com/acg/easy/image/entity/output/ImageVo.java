package com.acg.easy.image.entity.output;

import com.acg.easy.storage.entity.output.FileInfoVo;
import lombok.Getter;
import lombok.Setter;

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


    public static ImageVo transfer(FileInfoVo infoVo) {
        ImageVo imageVo = new ImageVo();
        imageVo.setId(UUID.randomUUID().toString());
        imageVo.setTitle(infoVo.getFilePath());
        imageVo.setName(infoVo.getFileName());
        imageVo.setImgNum("1");
        imageVo.setImgSrc(infoVo.getFilePath());
        imageVo.setGuid(UUID.randomUUID().toString());
        return imageVo;
    }
}
