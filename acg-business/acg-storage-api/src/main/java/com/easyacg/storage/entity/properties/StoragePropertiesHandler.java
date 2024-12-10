package com.easyacg.storage.entity.properties;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.handlers.Fastjson2TypeHandler;
import com.easyacg.storage.model.StorageModeEnum;

import java.lang.reflect.Field;

/**
 * 存储策略属性处理器
 * <p>
 * 主要用于与<b>数据库</b>交互的json处理器
 * </p>
 *
 * @author brahma
 */
public class StoragePropertiesHandler extends Fastjson2TypeHandler {
    public StoragePropertiesHandler(Class<?> type) {
        super(type);
    }

    public StoragePropertiesHandler(Class<?> type, Field field) {
        super(type, field);
    }

    @Override
    public StorageProperties parse(String json) {
        // type的值在创建更新时有做校验，因此不会出现其余的类型
        JSONObject jsonValue = JSONObject.parse(json);
        String type = jsonValue.getString("type");
        StorageModeEnum modeEnum = StorageModeEnum.valueOf(type);
        return JSON.parseObject(json, modeEnum.getPropertiesClazz());
    }
}
