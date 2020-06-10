package com.ewsie.allpic.config;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static lombok.AccessLevel.PRIVATE;

@ConfigurationProperties(prefix = "allpic")
@FieldDefaults(level = PRIVATE)
@Data
public class AppConfig {

    String awsRegion;
    String s3StorageBucket;

    @Length(min = 1)
    int imgTokenLength;

    @Length(min = 2)
    String imgTokenChars;

    int thumbSize;

    int mostRecentImagesCount;
}
