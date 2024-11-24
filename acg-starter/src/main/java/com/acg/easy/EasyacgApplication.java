package com.acg.easy;

import com.acg.easy.factory.EasyacgPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Brahma
 */
@SpringBootApplication
@PropertySource(value = {
        "classpath:application.yml", "classpath:application-image.yml", "classpath:application-wx.yml"
}, factory = EasyacgPropertySourceFactory.class)
@ComponentScan("com.acg.easy.*")
public class EasyacgApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyacgApplication.class, args);
    }
}
