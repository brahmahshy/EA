package com.easyacg.storage;

import com.easyacg.storage.properties.S3Properties;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

/**
 * S3Client初始化工具类
 *
 * @author brahma
 */
@Component
public class EasyacgS3Client {
    private static S3Properties s3Properties;

    @Resource
    public void setS3Properties(S3Properties properties) {
        s3Properties = properties;
    }

    public static S3Client build() {
        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(s3Properties.getAccessKey(), s3Properties.getSecretKey());

        S3Configuration serviceConfiguration = S3Configuration.builder().pathStyleAccessEnabled(true).build();

        return S3Client.builder()
                .endpointOverride(URI.create(s3Properties.getEndpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of("auto"))
                .serviceConfiguration(serviceConfiguration)
                .build();
    }
}
