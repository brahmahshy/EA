package com.acg.easy.storage.service;

import com.acg.easy.storage.StorageModeEnum;
import com.acg.easy.storage.entity.output.FileInfoVo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

/**
 * 存储策略服务
 *
 * @author brahma
 */
public interface StorageService {
    /**
     * 获取实现类对应存储类型
     *
     * @return 存储类型
     */
    StorageModeEnum getStorage();

    /**
     * 遍历获取所有路径的对象
     *
     * @return 对象集合
     */
    List<FileInfoVo> listObjects();

    /**
     * 上传对象
     */
    void putObject(InputStream file, Path path) throws IOException;
}
