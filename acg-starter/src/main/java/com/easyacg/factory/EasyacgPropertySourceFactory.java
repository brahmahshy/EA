package com.easyacg.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 自定义配置文件加载工厂
 * 支持 yml 和 properties 文件的加载
 * 当配置文件不存在时，返回空的配置源
 *
 * @author brahma
 */
@Slf4j
@Component
public class EasyacgPropertySourceFactory implements PropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        Resource resourceResource = resource.getResource();
        String filename = resourceResource.getFilename();
        String sourceName = Objects.requireNonNullElse(name, filename);

        // 资源不存在，返回空的PropertySource
        if (!resourceResource.exists()) {
            log.warn("配置文件 {} 不存在，返回空配置", filename);
            return new MapPropertySource(sourceName, Collections.emptyMap());
        }

        // 文件名为空，使用默认的ResourcePropertySource
        if (Objects.isNull(filename)) {
            log.debug("配置文件名为空，使用默认加载方式");
            return new ResourcePropertySource(sourceName, resource);
        }

        log.debug("正在加载配置文件: {}", filename);

        // 根据文件扩展名选择不同的加载方式
        if (filename.endsWith(".yml") || filename.endsWith(".yaml")) {
            List<PropertySource<?>> sources = new YamlPropertySourceLoader().load(filename, resourceResource);
            if (sources.isEmpty()) {
                log.warn("配置文件 {} 内容为空，返回空配置", filename);
                return new MapPropertySource(sourceName, Collections.emptyMap());
            }
            log.debug("成功加载YAML配置文件: {}", filename);
            return sources.getFirst();
        }

        // 其他类型文件使用默认的ResourcePropertySource加载
        log.debug("使用默认方式加载配置文件: {}", filename);
        return new ResourcePropertySource(sourceName, resource);
    }
}
