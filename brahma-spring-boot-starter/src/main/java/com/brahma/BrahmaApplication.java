package com.brahma;

import com.brahma.factory.BrahmaPropertySourceFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(
        value = {"classpath:application.yml", "classpath:application-photo.yml", "classpath:application-wx.yml",
                "classpath:application-security.yml"}
        , factory = BrahmaPropertySourceFactory.class
)
public class BrahmaApplication {
    public static void main(String[] args) {
        SpringApplication.run(BrahmaApplication.class, args);
    }
}
