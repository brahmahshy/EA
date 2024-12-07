package com.easyacg.storage.service;

import com.easyacg.storage.entity.output.FileInfoVo;
import com.easyacg.storage.model.StorageModeEnum;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

/**
 * 存储策略服务 —— 数据交互接口
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
