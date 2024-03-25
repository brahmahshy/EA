package com.brahma.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;

/**
 * 基础Do类，供所有Do类继承字段
 */
@Getter
@Setter
public class TableBaseDo {
    /**
     * 创建人id
     */
    private Integer creatorId;

    /**
     * 創建時間
     */
    @TableField(fill = INSERT)
    private LocalDateTime createTime;

    /**
     * 更新人id
     */
    private Integer updaterId;

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
}
