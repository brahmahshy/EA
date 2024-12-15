package com.easyacg.storage.entity.input.file;

import com.easyacg.storage.model.Storage;
import lombok.Builder;
import lombok.Getter;

import java.io.File;
import java.nio.file.Path;

/**
 * @author brahma
 */
@Getter
@Builder
public class PutObjectBo {
    /**
     * 存储策略名称
     */
    private Storage storage;

    /**
     * 需要上传的临时文件
     */
    private File file;

    /**
     * 文件上传后的保存路径
     */
    private Path path;
}
