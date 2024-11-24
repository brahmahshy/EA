package com.acg.easy.storage.service.impl;

import com.acg.easy.storage.StorageModeEnum;
import com.acg.easy.storage.properties.S3Properties;
import com.acg.easy.storage.service.StorageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

/**
 * S3通用存储配置
 *
 * @author barhma
 */
@Slf4j
@Service
public class DefaultS3StorageServiceImpl implements StorageService<S3Object> {
    static S3AsyncClient s3AsyncClient;

    @Resource
    private S3Properties s3Properties;

    @Override
    public StorageModeEnum getStorage() {
        return StorageModeEnum.S3;
    }

    @Override
    public List<S3Object> listObjects() {
        //        S3Client s3Client = buildS3Client();
        //        String bucketName = s3Properties.getBucketName();
        //        try (s3Client) {
        //            ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucketName).build();
        //
        //
        //            PutObjectRequest build = PutObjectRequest.builder().bucket("").key("").build();
        //            s3Client.putObject(build, Paths.get(""));
        //
        //            return s3Client.listObjectsV2(request).contents();
        //        } catch (S3Exception e) {
        //            throw EasyacgException.build(e, "Failed to list objects in bucket {0}: ", bucketName);
        //        }
        return null;
    }

    @Override
    public void putObject(InputStream file, Path path) {

    }
}
