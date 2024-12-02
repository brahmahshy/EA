package com.acg.easy.image.entity;

import com.acg.easy.core.entity.TableBaseDo;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 图像表
 *
 * @author admin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "x_image", schema = "easyacg")
public class ImageDo extends TableBaseDo {
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
    private Long photographerId;

    /**
     * 模特id
     */
    private Long modelId;

    /**
     * 拍摄地点
     */
    private String location;
}
