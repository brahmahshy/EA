package com.easyacg.storage.service.impl;

import com.easyacg.storage.S3Util;
import com.easyacg.storage.StorageModeEnum;
import com.easyacg.storage.properties.R2Properties;
import com.easyacg.storage.properties.S3Properties;
import com.easyacg.storage.service.StorageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

/**
 * Cloudflare R2 存储策略操作实现类
 *
 * @author brahma
 */
@Slf4j
@Service
public class CloudflareR2StorageServiceImpl extends DefaultS3StorageServiceImpl implements StorageService {
    @Resource
    private R2Properties r2Properties;

    @Override
    public StorageModeEnum getStorage() {
        return StorageModeEnum.R2;
    }

    public static void main(String[] args) {
        S3Properties s3Properties = new S3Properties();
        s3Properties.setAccessKey("07af84fb73f27dfb0fe347b7dcb36e0b");
        s3Properties.setSecretKey("91259032ff52eadb21e3b7cf4b8743bb29b1450a7cc1a7134bb469c900cc7fd7");
        s3Properties.setEndpoint("https://4babbbc62a173e234c3bb185c76a41f5.r2.cloudflarestorage.com");

        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(s3Properties.getAccessKey(), s3Properties.getSecretKey());

        S3Configuration serviceConfiguration = S3Configuration.builder().pathStyleAccessEnabled(true).build();

        S3Util.s3Client = S3Client.builder()
                .endpointOverride(URI.create(s3Properties.getEndpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of("auto"))
                .serviceConfiguration(serviceConfiguration)
                .build();

        S3Util.listObjects("easyacg-small")
                .forEach(object -> System.out.printf(
                        "* %s (size: %d bytes, modified: %s)%n",
                        object.key(),
                        object.size(),
                        object.lastModified()
                ));

    }
}
