package com.acg.easy.storage.entity.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.io.InputStream;
import java.time.LocalDateTime;

/**
 * 文件信息包装类
 *
 * @author brahma
 */
@Data
@Builder
public class FileInfoVo {
    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件大小（字节）
     */
    private long fileSize;

    /**
     * 文件输入流
     */
    @JsonIgnore
    private InputStream inputStream;

    /**
     * 最后修改时间
     */
    private LocalDateTime lastModified;

    /**
     * 文件类型/扩展名
     */
    private String fileType;

    {
        // todo 接口结束后，inputStream要关掉
    }
} 