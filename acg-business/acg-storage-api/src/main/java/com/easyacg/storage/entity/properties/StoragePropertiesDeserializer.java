package com.easyacg.storage.entity.properties;

import com.easyacg.storage.model.StorageModeEnum;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * 存储属性反序列化器
 * <p>
 * 主要用于与<b>前端接口</b>交互的json处理器
 * </p>
 *
 * @author brahma
 */
public class StoragePropertiesDeserializer extends JsonDeserializer<StorageProperties> {
    @Override
    public StorageProperties deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode node = mapper.readTree(p);
        String type = node.get("type").asText();

        StorageProperties properties;
        try {
            StorageModeEnum mode = StorageModeEnum.valueOf(type);
            properties = mapper.treeToValue(node, mode.getPropertiesClazz());
        } catch (IllegalArgumentException e) {
            properties = mapper.treeToValue(node, StorageProperties.class);
        }
        properties.init();
        return properties;
    }
}