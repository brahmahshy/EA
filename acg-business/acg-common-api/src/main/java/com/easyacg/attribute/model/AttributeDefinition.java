package com.easyacg.attribute.model;

import com.easyacg.attribute.constant.AttributeType;
import com.easyacg.core.mybatis.TableBaseDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.autotable.annotation.ColumnComment;
import org.dromara.mpe.autotable.annotation.Table;

@Data
@Table
@EqualsAndHashCode(callSuper = true)
public class AttributeDefinition extends TableBaseDo {
    @ColumnComment("属性key值")
    private String key;

    @ColumnComment("属性中文名称")
    private String name;

    @ColumnComment("属性类型")
    private AttributeType attrType;
}
