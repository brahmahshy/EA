package com.easyacg.image.entity.output;

import com.easyacg.image.mybatis.ImageDo;
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


    public static ImageVo transfer(ImageDo imageDo) {
        ImageVo imageVo = new ImageVo();
        imageVo.setId(imageDo.getId().toString());
        imageVo.setName(imageDo.getName());
        imageVo.setImgSrc(imageDo.getFilePath());
        return imageVo;
    }
}
