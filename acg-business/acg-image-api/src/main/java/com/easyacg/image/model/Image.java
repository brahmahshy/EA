package com.easyacg.image.model;

import com.easyacg.core.mybatis.TableBaseDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.autotable.annotation.ColumnComment;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.Table;
import org.dromara.mpe.autotable.annotation.UniqueIndex;

import java.time.LocalDateTime;

/**
 * 图像表
 *
 * @author admin
 */
@Data
@Table
@EqualsAndHashCode(callSuper = true)
public class Image extends TableBaseDo {
    @UniqueIndex
    @Column(comment = "图像名称", notNull = true)
    private String name;

    @ColumnComment("图像别名")
    private String aliseName;

    @ColumnComment("文件路径")
    private String filePath;

    @ColumnComment("拍摄时间")
    private LocalDateTime shootingTime;

    @ColumnComment("摄影师id")
    private Long photographerId;

    @ColumnComment("模特id")
    private Long modelId;

    @ColumnComment("拍摄地点")
    private String location;

    @ColumnComment("存储策略id")
    private Long storageId;

    @ColumnComment("文件md5值")
    private String md5Hex;
}
