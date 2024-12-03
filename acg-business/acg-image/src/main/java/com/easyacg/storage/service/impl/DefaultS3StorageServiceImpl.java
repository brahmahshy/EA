package com.easyacg.storage.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import com.easyacg.core.entity.EasyacgException;
import com.easyacg.storage.S3Util;
import com.easyacg.storage.entity.output.FileInfoVo;
import com.easyacg.storage.mybatis.enums.StorageModeEnum;
import com.easyacg.storage.properties.S3Properties;
import com.easyacg.storage.service.StorageBusinessService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * S3通用存储配置
 *
 * @author barhma
 */
@Slf4j
@Service
public class DefaultS3StorageServiceImpl implements StorageBusinessService {
    @Resource
    private S3Properties s3Properties;

    @Override
    public StorageModeEnum getStorage() {
        return StorageModeEnum.S3;
    }

    @Override
    public List<FileInfoVo> listObjects() {
        String bucketName = s3Properties.getBucketName();
        try {
            // 使用项目自己的S3Util工具类获取对象列表
            List<S3Object> s3Objects = S3Util.listObjects(bucketName);
            List<FileInfoVo> result = new ArrayList<>();

            // 将S3Object转换为FileInfoVo，并获取每个对象的输入流
            for (S3Object obj : s3Objects) {
                // 获取对象的输入流
                GetObjectRequest getObjectRequest =
                        GetObjectRequest.builder().bucket(bucketName).key(obj.key()).build();

                ResponseInputStream<GetObjectResponse> objectData = S3Util.s3Client.getObject(getObjectRequest);

                // 构建FileInfoVo对象
                FileInfoVo info = FileInfoVo.builder()
                        .fileName(obj.key())
                        .filePath(obj.key())
                        .fileSize(obj.size())
                        .inputStream(objectData)
                        .lastModified(LocalDateTimeUtil.of(obj.lastModified()))
                        .fileType(FileUtil.extName(obj.key()))
                        .build();

                result.add(info);
            }

            return result;
        } catch (Exception e) {
            log.error("获取存储桶[{}]中的对象列表失败", bucketName, e);
            throw EasyacgException.build(e, "获取存储桶对象列表失败");
        }
    }

    @Override
    public void putObject(InputStream file, Path path) {
        // putObject实现待添加
    }
}
