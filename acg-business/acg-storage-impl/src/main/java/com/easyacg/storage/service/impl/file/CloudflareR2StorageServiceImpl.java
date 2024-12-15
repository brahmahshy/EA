package com.easyacg.storage.service.impl.file;

import com.easyacg.storage.model.StorageModeEnum;
import com.easyacg.storage.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Cloudflare R2 存储策略操作实现类
 *
 * @author brahma
 */
@Slf4j
@Service
public class CloudflareR2StorageServiceImpl extends DefaultS3StorageServiceImpl implements FileService {
    @Override
    public StorageModeEnum getType() {
        return StorageModeEnum.R2;
    }
}
