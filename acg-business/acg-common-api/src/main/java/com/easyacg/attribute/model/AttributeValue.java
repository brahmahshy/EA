package com.easyacg.attribute.model;


import com.easyacg.attribute.model.define.AttributeDefinitionDefine;
import com.easyacg.attribute.model.define.AttributeValueDefine;
import com.easyacg.core.constant.enums.ObjectType;
import com.easyacg.core.mybatis.TableBaseDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.autotable.annotation.ColumnComment;
import org.dromara.mpe.autotable.annotation.Table;
import org.dromara.mpe.bind.metadata.annotation.BindField;
import org.dromara.mpe.bind.metadata.annotation.JoinCondition;

@Data
@Table
@EqualsAndHashCode(callSuper = true)
public class AttributeValue extends TableBaseDo {
    @ColumnComment("属性定义id")
    private Long attrDefId;

    @ColumnComment("对象id")
    private Long objectId;

    @ColumnComment("对象软类型")
    private ObjectType objectType;

    @ColumnComment("属性值")
    private String value;

    @BindField(entity = AttributeDefinition.class, field = AttributeDefinitionDefine.key,
            conditions = @JoinCondition(selfField = AttributeValueDefine.attrDefId))
    private String attrDefKey;

    @BindField(entity = AttributeDefinition.class, field = AttributeDefinitionDefine.name,
            conditions = @JoinCondition(selfField = AttributeValueDefine.attrDefId))
    private String attrDefName;
}
