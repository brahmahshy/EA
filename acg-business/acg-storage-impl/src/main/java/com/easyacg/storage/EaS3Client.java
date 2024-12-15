package com.easyacg.storage;

import cn.hutool.core.io.FileUtil;
import com.easyacg.core.entity.EasyacgException;
import com.easyacg.storage.entity.dto.FileChunkDto;
import com.easyacg.storage.entity.dto.S3MultipartDto;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * S3操作工具类
 * 提供S3存储的基础操作，包括文件上传、列表查询和分片上传等功能
 *
 * <p>参考:
 * <a href="https://github.com/awsdocs/aws-doc-sdk-examples/blob/main/javav2/example_code/s3/src/main/java/com/example/s3/scenario/S3Actions.java">S3Actions</a>
 * </p>
 *
 * @author brahma
 */
@Slf4j
@UtilityClass
public class EaS3Client {
    /**
     * 分片大小阈值：5MB
     */
    private static final int CONTENT_LENGTH = 5 * 1024 * 1024;

    /**
     * S3客户端实例
     */
    public static final ThreadLocal<S3Client> S3_CLIENT = new ThreadLocal<>();

    private S3Client getClient() {
        S3Client s3Client = S3_CLIENT.get();
        if (s3Client == null) {
            throw EasyacgException.build("S3客户端未初始化");
        }
        return s3Client;
    }

    /**
     * 列出指定桶中的所有对象
     *
     * @param bucketName S3桶名称
     * @return 桶中所有对象的列表
     */
    public List<S3Object> listObjects(String bucketName) {
        log.info("正在获取桶[{}]中的所有对象", bucketName);
        ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucketName).build();
        List<S3Object> objects = getClient().listObjectsV2(request).contents();
        log.info("成功获取桶[{}]中的对象，共{}个", bucketName, objects.size());
        return objects;
    }

    /**
     * <h>上传文件到S3存储</h>
     * <p>
     * 根据文件大小自动选择普通上传或分片上传：
     * <li>文件大小 <= 5MB：使用普通上传</li>
     * <li>文件大小 > 5MB：使用分片上传</li>
     * </p>
     *
     * @param bucketName S3桶名称
     * @param objectName 对象名称（存储在S3中的文件名）
     * @param file       要上传的本地文件
     */
    public void uploadObjects(String bucketName, String objectName, File file) {

        // 判断文件大小是否超过5MB阈值
        long fileSize = file.length();
        if (fileSize <= CONTENT_LENGTH) {
            // 小于5MB使用普通上传
            upload(bucketName, objectName, RequestBody.fromFile(file));
        } else {
            // 创建分片上传请求
            S3MultipartDto multipartDto = new S3MultipartDto();
            multipartDto.setBucketName(bucketName);
            multipartDto.setObjectName(objectName);
            multipartDto.setUploadId(createMultipartUpload(multipartDto));

            // 分片上传
            List<CompletedPart> parts = splitFile(file).stream()
                    .map(chunk -> multipartUpload(multipartDto, chunk))
                    .collect(Collectors.toList());

            completeMultipartUpload(multipartDto, parts);
            log.info("分片上传完成 - 桶: {}, 对象名: {}", bucketName, objectName);
        }
    }

    /**
     * 使用本地路径上传文件到S3存储
     * 适用于小文件的简单上传
     *
     * @param bucketName  S3桶名称
     * @param objectName  对象名称（存储在S3中的文件名）
     * @param requestBody 要上传的文件内容
     */
    public void upload(String bucketName, String objectName, RequestBody requestBody) {
        log.info("开始上传文件 - 桶: {}, 对象名: {}", bucketName, objectName);
        PutObjectRequest request = PutObjectRequest.builder().bucket(bucketName).key(objectName).build();
        getClient().putObject(request, requestBody);
        log.info("文件上传成功 - 桶: {}, 对象名: {}", bucketName, objectName);
    }

    /**
     * 初始化分片上传任务
     * 创建一个新的分片上传ID，用于后续的分片上传操作
     *
     * @param multipartDto 分片上传参数对象，包含桶名和对象名
     * @return 分片上传ID
     */
    public String createMultipartUpload(S3MultipartDto multipartDto) {
        log.info("开始创建分片上传 - 桶: {}, 对象名: {}", multipartDto.getBucketName(), multipartDto.getObjectName());
        CreateMultipartUploadRequest request = CreateMultipartUploadRequest.builder()
                .bucket(multipartDto.getBucketName())
                .key(multipartDto.getObjectName())
                .build();
        String uploadId = getClient().createMultipartUpload(request).uploadId();
        log.info("分片上传创建成功 - uploadId: {}", uploadId);
        return uploadId;
    }

    /**
     * 上传单个分片
     * 将文件的一个分片上传到S3存储
     *
     * @param multipartDto 分片���传参数对象，包含桶名、对象名和上传ID
     * @param chunk        分片数据对象，包含分片号、数据等信息
     * @return 已完成的分片信息，包含分片号和ETag
     */
    public CompletedPart multipartUpload(S3MultipartDto multipartDto, FileChunkDto chunk) {
        int partNumber = chunk.getChunkNumber();
        log.info(
                "开始上传分片 - 桶: {}, 对象名: {}, 分片号: {}",
                multipartDto.getBucketName(),
                multipartDto.getObjectName(),
                partNumber
        );

        // 构建分片上传请求
        UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
                .bucket(multipartDto.getBucketName())
                .key(multipartDto.getObjectName())
                .uploadId(multipartDto.getUploadId())
                .partNumber(partNumber)
                .contentLength((long) chunk.getSize())
                .build();

        // 执行分片上传
        UploadPartResponse response = getClient().uploadPart(uploadPartRequest, RequestBody.fromBytes(chunk.getData()));

        // 返回分片上传的ETag
        CompletedPart part = CompletedPart.builder().partNumber(partNumber).eTag(response.eTag()).build();

        // 记录分片上传结果
        log.info("分片{}上传完成,ETag:{}", partNumber, part.eTag());
        return part;
    }

    /**
     * 完成分片上传
     * 合并所有已上传的分片，完成整个文件的上传
     *
     * @param multipartDto 分片上传参数对象
     * @param parts        所有已完成分片的列表
     */
    public void completeMultipartUpload(S3MultipartDto multipartDto, List<CompletedPart> parts) {
        log.info(
                "开始完成分片上传 - 桶: {}, 对象名: {}, uploadId: {}, 总分片数: {}",
                multipartDto.getBucketName(),
                multipartDto.getObjectName(),
                multipartDto.getUploadId(),
                parts.size()
        );

        CompletedMultipartUpload completedMultipartUpload = CompletedMultipartUpload.builder().parts(parts).build();

        CompleteMultipartUploadRequest request = CompleteMultipartUploadRequest.builder()
                .bucket(multipartDto.getBucketName())
                .key(multipartDto.getObjectName())
                .uploadId(multipartDto.getUploadId())
                .multipartUpload(completedMultipartUpload)
                .build();
        getClient().completeMultipartUpload(request);

        log.info("分片上传已完成 - 桶: {}, 对象名: {}", multipartDto.getBucketName(), multipartDto.getObjectName());
    }

    /**
     * ���文件分割成指定大小的分片
     * 用于大文件的分片上传准备
     *
     * @param file 要分片的源文件信息对象
     * @return 分片信息列表
     * @throws EasyacgException 当文件读取失败时抛出
     */
    private static List<FileChunkDto> splitFile(File file) {
        log.info(
                "开始分片文件: {}, 文件大小: {} bytes, 分片大小: {} bytes",
                file.getName(),
                file.length(),
                CONTENT_LENGTH
        );

        List<FileChunkDto> chunks = new ArrayList<>();

        // 文件总大小
        long fileSize = file.length();
        // 当前处理位置
        long position = 0;
        // 分片序号，从1开始
        int chunkNumber = 1;

        // 读取所有字节
        byte[] fileBytes = FileUtil.readBytes(file);

        while (position < fileSize) {
            // 计算当前分片大小
            int currentChunkSize = (int) Math.min(CONTENT_LENGTH, fileSize - position);
            byte[] chunk = new byte[currentChunkSize];

            // 复制对应部分的数据
            System.arraycopy(fileBytes, (int) position, chunk, 0, currentChunkSize);

            // 创建分片对象
            FileChunkDto fileChunk = FileChunkDto.builder()
                    .chunkNumber(chunkNumber)
                    .startPosition(position)
                    .size(currentChunkSize)
                    .data(chunk)
                    .build();

            chunks.add(fileChunk);
            position += currentChunkSize;
            chunkNumber++;
        }

        log.info("文件分片完成 - 文件: {}, 总分片数: {}", file.getName(), chunks.size());
        return chunks;
    }
}
