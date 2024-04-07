package com.brahma.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import com.brahma.entity.PhotoDo;
import com.brahma.mapper.PhotoMapper;
import com.brahma.service.PhotoService;
import com.brahma.util.SnowIdWorker;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class PhotoServiceImpl implements PhotoService {
    @Resource
    private PhotoMapper photoMapper;

    @Resource
    private SnowIdWorker snowIdWorker;

    @Resource
    private PlatformTransactionManager transactionManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void readPhoto(List<File> files) {
        // todo 直接从本地磁盘读取，后续需要优化为使用 NAS 或 S3 进行存储
        // todo 后续需要优化为多线程执行，或者 批量插入
        for (File file : files) {
            photoMapper.insert(new PhotoDo() {{
                this.setName(file.getName());
                this.setAliseName("");
                this.setFilePath(file.getAbsolutePath());
                this.setShootingStartTime(LocalDateTime.now());
                this.setShootingEndTime(LocalDateTime.now());
                this.setPhotographerId(0L);
                this.setModelId(0L);
                this.setLocation("");
            }});
        }
    }
}
