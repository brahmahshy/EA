package com.acg.easy.core.entity;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;

/**
 * 基础Do类，供所有Do类继承字段
 *
 * @author admin
 */
@Data
public class TableBaseDo {
    /**
     * 主键id
     */
    @TableId(type = IdType.INPUT)
    private Long id;

    /**
     * 创建人id
     */
    @TableField(fill = INSERT)
    private Long creatorId;

    /**
     * 創建時間
     */
    @TableField(fill = INSERT)
    private LocalDateTime createTime;

    /**
     * 更新人id
     */
    @TableField(fill = INSERT_UPDATE)
    private Long updaterId;

    /**
     * 更新時間
     */
    @TableField(fill = INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 更新次數
     */
    @TableField(update = "%s + 1", updateStrategy = FieldStrategy.ALWAYS, fill = INSERT)
    private Integer updateCount;

    public TableBaseDo() {
        this.id = IdUtil.getSnowflakeNextId();
    }
}
