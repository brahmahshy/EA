package com.easyacg;

import com.easyacg.attribute.entity.IbaAttribute;
import com.easyacg.core.mybatis.TableBaseDo;

import java.util.List;

public interface AttributeValueService {
    boolean addAttributeValue(TableBaseDo baseDo, List<IbaAttribute> ibaList);
}
