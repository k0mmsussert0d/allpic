package com.ewsie.allpic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableScheduling
public class OtherBeans {

    @Bean
    Map<String, String> extensionToMimeTypeMappings() {
        Map<String, String> res = new HashMap<>();

        res.put("jpg", "image/jpeg");
        res.put("jpeg", "image/jpeg");
        res.put("png", "image/png");
        res.put("gif", "image/gif");

        return res;
    }
}
