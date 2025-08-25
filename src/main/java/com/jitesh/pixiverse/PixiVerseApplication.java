package com.jitesh.pixiverse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PixiVerseApplication {

    public static void main(String[] args) {
        SpringApplication.run(PixiVerseApplication.class, args);
    }

}
