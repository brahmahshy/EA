package com.easyacg.core.mybatis;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.dromara.autotable.annotation.ColumnComment;
import org.dromara.mpe.annotation.*;
import org.dromara.mpe.autotable.annotation.ColumnId;

import java.time.LocalDateTime;

/**
 * 基础Do类，供所有Do类继承字段
 *
 * @author admin
 */
@Data
public class TableBaseDo {
    @ColumnId(mode = IdType.ASSIGN_ID, comment = "主键id")
    protected Long id;

    @ColumnComment("创建人id")
    @InsertFillData(UserIdAutoFillHandler.class)
    protected Long creatorId;

    @InsertFillTime
    @ColumnComment("创建时间")
    protected LocalDateTime createTime;

    @ColumnComment("更新人id")
    @InsertUpdateFillData(UserIdAutoFillHandler.class)
    protected Long updaterId;

    @InsertUpdateFillTime
    @ColumnComment("更新時間")
    protected LocalDateTime updateTime;

    @DefaultValue("0")
    @ColumnComment("更新次數")
    @TableField(update = "%s + 1", updateStrategy = FieldStrategy.ALWAYS)
    protected Integer updateCount;
}
