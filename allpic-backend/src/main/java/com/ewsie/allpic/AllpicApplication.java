package com.ewsie.allpic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class AllpicApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllpicApplication.class, args);
    }

}
