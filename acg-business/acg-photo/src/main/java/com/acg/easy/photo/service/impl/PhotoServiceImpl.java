package com.acg.easy.photo.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileTypeUtil;
import com.acg.easy.photo.entity.output.PhotoVo;
import com.acg.easy.photo.mapper.PhotoMapper;
import com.acg.easy.photo.service.PhotoService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Slf4j
@Service
public class PhotoServiceImpl implements PhotoService {
    @Resource
    private PhotoMapper photoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PhotoVo> readPhoto(List<File> files) {
        // todo 直接从本地磁盘读取，后续需要优化为使用 NAS 或 S3 进行存储
        // todo 后续需要优化为多线程执行，或者 批量插入
        // todo 使用多线程需要注意 事务安全问题
        if (CollectionUtil.isEmpty(files)) {
            return null;
        }

//        files.forEach(file -> {
//            photoMapper.insert(new PhotoDo() {{
//                this.setName(file.getName());
//                this.setAliseName("");
//                this.setFilePath(file.getAbsolutePath());
//                this.setShootingStartTime(LocalDateTime.now());
//                this.setShootingEndTime(LocalDateTime.now());
//                this.setPhotographerId(0L);
//                this.setModelId(0L);
//                this.setLocation("");
//            }});
//        });

        return files.stream().filter(file -> "jpg".equals(FileTypeUtil.getType(file))).limit(1).map(PhotoVo::transfer).toList();
    }
}
