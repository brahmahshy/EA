package com.easyacg.core.handler;

import org.dromara.mpe.annotation.handler.AutoFillHandler;

import java.lang.reflect.Field;

/**
 * 新增数据时，自动插入对象软类型
 *
 * @author brahma
 */
public class ObjectTypeAutoFillHandler implements AutoFillHandler<String> {
    /**
     * 获取解析后的数据，并作为
     *
     * @param object 当前操作的数据对象
     * @param clazz  当前操作的数据对象的class
     * @param field  当前操作的数据对象上的字段
     * @return 用户id
     */
    @Override
    public String getVal(Object object, Class<?> clazz, Field field) {
        return "";
    }
}
