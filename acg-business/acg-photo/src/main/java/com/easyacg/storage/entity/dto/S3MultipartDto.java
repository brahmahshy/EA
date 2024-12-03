package com.easyacg.storage.entity.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * S3分片上传中间类
 *
 * @author brahma
 */
@Getter
@Setter
public class S3MultipartDto {
    /**
     * 桶名称，用于确认上传到哪个桶
     */
    private String bucketName;

    /**
     * 对象名称，用于确认对象上传路径，及对象名称
     */
    private String objectName;

    /**
     * 上传id，创建分片上传后封装，用于后续确认分片的是同一个文件
     */
    private String uploadId;
}
