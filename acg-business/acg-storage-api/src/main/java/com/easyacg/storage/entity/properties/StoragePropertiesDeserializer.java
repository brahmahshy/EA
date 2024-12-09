package com.easyacg.storage.entity.properties;

import com.easyacg.storage.model.StorageModeEnum;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * 特殊的解析器，根据不同的类型，反序列化不同的对象
 *
 * @author brahma
 */
public class StoragePropertiesDeserializer extends JsonDeserializer<StorageProperties> {
    @Override
    public StorageProperties deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode node = mapper.readTree(p);

        JsonNode typeNode = node.get("type");
        StorageModeEnum mode = StorageModeEnum.valueOf(typeNode.asText());

        StorageProperties properties = mapper.treeToValue(node, mode.getPropertiesClazz());
        properties.init();
        return properties;
    }
}