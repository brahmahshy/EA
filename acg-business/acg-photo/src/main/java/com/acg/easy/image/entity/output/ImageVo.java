package com.acg.easy.image.entity.output;

import com.acg.easy.core.entity.EasyacgException;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.services.s3.model.S3Object;

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

    public static ImageVo transfer(Object object) {
        if (object instanceof File file) {
            return transfer(file);
        } else if (object instanceof S3Object s3Object) {
            return transfer(s3Object);
        } else {
            throw EasyacgException.build("类型问题");
        }
    }

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

    public static ImageVo transfer(S3Object s3Object) {
        ImageVo imageVo = new ImageVo();
        imageVo.setId(UUID.randomUUID().toString());
        imageVo.setTitle(s3Object.key());
        imageVo.setName(s3Object.key());
        imageVo.setImgNum("1");
        imageVo.setImgSrc(s3Object.key());
        imageVo.setGuid(UUID.randomUUID().toString());
        return imageVo;
    }
}
