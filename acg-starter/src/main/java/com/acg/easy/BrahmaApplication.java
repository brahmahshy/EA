package com.acg.easy;

import com.acg.easy.factory.BrahmaPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"classpath:application.yml", "classpath:application-wx.yml"}
        , factory = BrahmaPropertySourceFactory.class)
@ComponentScan("com.acg.easy.*")
public class BrahmaApplication {
    public static void main(String[] args) {
        SpringApplication.run(BrahmaApplication.class, args);
    }
}
