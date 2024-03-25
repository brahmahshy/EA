package com.brahma.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class PhotoDo extends TableBaseDo {
    /**
     * id主键
     */
    private BigInteger id;

    /**
     * 照片名称
     */
    private String name;

    /**
     * 别名
     */
    private String aliseName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 拍摄开始时间
     */
    private LocalDateTime shootingStartTime;

    /**
     * 拍摄结束时间
     */
    private LocalDateTime shootingEndTime;

    /**
     * 摄影师id
     */
    private BigInteger photographerId;

    /**
     * 模特id
     */
    private BigInteger modelId;

    /**
     * 是否关联多个模特
     */
    private Boolean hasMoreModel;

    /**
     * 拍摄地点
     */
    private String location;
}
