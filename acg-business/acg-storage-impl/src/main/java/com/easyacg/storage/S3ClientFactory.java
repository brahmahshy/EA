package com.easyacg.storage;

import com.easyacg.core.entity.EasyacgException;
import com.easyacg.storage.entity.properties.S3Properties;
import com.easyacg.storage.entity.properties.StorageProperties;
import com.easyacg.storage.model.Storage;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * S3客户端工厂
 *
 * @author brahma
 */
public class S3ClientFactory {
    public static final Map<String, S3Client> S3_CLIENT_MAP = new ConcurrentHashMap<>();

    public static void buildAndSetS3Client(Storage storage) {
        if (storage == null) {
            throw EasyacgException.build("存储策略不存在！！！");
        }

        StorageProperties properties = storage.getProperties();
        if (properties instanceof S3Properties s3Properties) {
            S3Client s3Client = S3_CLIENT_MAP.computeIfAbsent(storage.getName(), key -> build(s3Properties));
            EaS3Client.S3_CLIENT.set(s3Client);
            return;
        }

        throw EasyacgException.build("不是S3类型存储策略！！！");
    }

    private static S3Client build(S3Properties s3Properties) {
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
