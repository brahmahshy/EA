package com.acg.easy.storage.entity;

import lombok.Builder;
import lombok.Data;

/**
 * 文件分片信息
 *
 * @author brahma
 */
@Data
@Builder
public class FileChunkDto {
    /**
     * 分片序号
     */
    private int chunkNumber;

    /**
     * 分片在文件中的起始位置
     */
    private long startPosition;

    /**
     * 分片大小
     */
    private int size;

    /**
     * 分片数据
     */
    private byte[] data;
} 