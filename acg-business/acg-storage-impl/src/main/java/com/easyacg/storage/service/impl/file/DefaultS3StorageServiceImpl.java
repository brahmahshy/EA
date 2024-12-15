package com.easyacg.storage.service.impl.file;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import com.easyacg.core.entity.EasyacgException;
import com.easyacg.storage.EaS3Client;
import com.easyacg.storage.aspect.InitS3Client;
import com.easyacg.storage.entity.input.file.PutObjectBo;
import com.easyacg.storage.entity.output.FileInfoVo;
import com.easyacg.storage.entity.properties.S3Properties;
import com.easyacg.storage.entity.properties.StorageProperties;
import com.easyacg.storage.model.Storage;
import com.easyacg.storage.model.StorageModeEnum;
import com.easyacg.storage.repository.StorageRepository;
import com.easyacg.storage.service.FileService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * S3通用存储配置
 *
 * @author barhma
 */
@Slf4j
@Service
public class DefaultS3StorageServiceImpl implements FileService {
    @Resource
    private StorageRepository storageRepository;

    @Override
    public StorageModeEnum getType() {
        return StorageModeEnum.S3;
    }

    @Override
    @InitS3Client
    public List<FileInfoVo> listObjects() {
        S3Properties s3Properties = new S3Properties();
        String bucketName = s3Properties.getBucketName();
        try {
            // 使用项目自己的S3Util工具类获取对象列表
            List<S3Object> s3Objects = EaS3Client.listObjects(bucketName);
            List<FileInfoVo> result = new ArrayList<>();

            // 将S3Object转换为FileInfoVo，并获取每个对象的输入流
            for (S3Object obj : s3Objects) {
                // 获取对象的输入流
                GetObjectRequest getObjectRequest =
                        GetObjectRequest.builder().bucket(bucketName).key(obj.key()).build();

                ResponseInputStream<GetObjectResponse> objectData =
                        EaS3Client.S3_CLIENT.get().getObject(getObjectRequest);

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
    @InitS3Client
    public String putObject(PutObjectBo putObjectBo) {
        Storage storage = putObjectBo.getStorage();
        StorageProperties properties = storage.getProperties();

        if (!(properties instanceof S3Properties s3Properties)) {
            throw EasyacgException.build("不是S3类型存储策略！！！");
        }

        // 上传文件到S3
        File file = putObjectBo.getFile();
        EaS3Client.uploadObjects(s3Properties.getBucketName(), file.getName(), file);
        log.info("文件上传成功 - 存储桶: {}, 对象名: {}", s3Properties.getBucketName(), file.getName());
        return file.getName();
    }
}
