package com.ewsie.allpic;

import com.ewsie.allpic.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
public class AllpicApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllpicApplication.class, args);
    }

}
