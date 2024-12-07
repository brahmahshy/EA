package com.easyacg.image.entity.output;

import com.easyacg.image.model.Image;
import lombok.Getter;
import lombok.Setter;

/**
 * @author brahma
 */
@Getter
@Setter
public class ImageVo {
    private String id;
    private String name;
    private String imgSrc;


    public static ImageVo transfer(Image image) {
        ImageVo imageVo = new ImageVo();
        imageVo.setId(image.getId().toString());
        imageVo.setName(image.getName());
        imageVo.setImgSrc(image.getFilePath());
        return imageVo;
    }
}
