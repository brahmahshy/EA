package com.easyacg.core.mybatis;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.autotable.annotation.ColumnComment;
import org.dromara.autotable.annotation.ColumnDefault;

/**
 * 基础Do类，供所有Do类继承字段
 *
 * @author admin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogicTableBaseDo extends TableBaseDo {
    @TableLogic
    @ColumnDefault("0")
    @ColumnComment("是否删除")
    protected Integer isDeleted;
}
