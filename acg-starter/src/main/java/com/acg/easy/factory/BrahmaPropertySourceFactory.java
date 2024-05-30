package com.acg.easy.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class BrahmaPropertySourceFactory implements PropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        Resource resourceResource = resource.getResource();
        if (!resourceResource.exists()) {
            log.error("The resource {} is not exist!!!", resourceResource.getFilename());
            return new ResourcePropertySource(resource);
        }

        if (Objects.isNull(resourceResource.getFilename())) {
            return new ResourcePropertySource(name, resource);
        }

        log.debug("read resource: {} successful.", resourceResource.getFilename());
        if (resourceResource.getFilename().endsWith(".yml")
                || resourceResource.getFilename().endsWith(".yaml")
        ) {

            List<PropertySource<?>> sources = new YamlPropertySourceLoader()
                    .load(resourceResource.getFilename(), resourceResource);
            return sources.getFirst();
        }

        return new ResourcePropertySource(name, resource);
    }
}
