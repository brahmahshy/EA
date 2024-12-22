package com.easyacg.attribute.service.impl;

import com.easyacg.AttributeValueService;
import com.easyacg.attribute.entity.IbaAttribute;
import com.easyacg.attribute.model.AttributeDefinition;
import com.easyacg.attribute.repository.AttributeDefinitionRepository;
import com.easyacg.attribute.repository.AttributeValueRepository;
import com.easyacg.core.entity.EasyacgException;
import com.easyacg.core.mybatis.TableBaseDo;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AttributeValueServiceImpl implements AttributeValueService {
    @Resource
    private AttributeDefinitionRepository attributeDefinitionRepository;

    @Resource
    private AttributeValueRepository attributeValueRepository;

    @Override
    public boolean addAttributeValue(TableBaseDo baseDo, List<IbaAttribute> ibaList) {
        List<String> keyList = ibaList.stream().map(IbaAttribute::getKey).collect(Collectors.toList());

        List<AttributeDefinition> def = attributeDefinitionRepository.lambdaQueryPlus().in(AttributeDefinition::getKey, keyList).list();
        Collection<String> subtract = CollectionUtils.subtract(keyList, def.stream().map(AttributeDefinition::getKey).collect(Collectors.toList()));
        if (CollectionUtils.isNotEmpty(subtract)) {
            // 存在差集，即 keyList 有，但 def 中不存在的属性
            throw EasyacgException.build("存在未定义的IBA属性：{}，请检查！！！", subtract);
        }

        boolean isSave = attributeValueRepository.save();
        return false;
    }
}
