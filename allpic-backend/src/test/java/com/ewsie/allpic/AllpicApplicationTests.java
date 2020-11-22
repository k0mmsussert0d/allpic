package com.ewsie.allpic;

import com.ewsie.allpic.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class AllpicApplicationTests {

    @Autowired
    private AppConfig appConfig;

    @Test
    public void shouldTestResourceFile_overridePropertyValues() {
        String env = appConfig.getEnvironment();

        assertThat(env).isEqualTo("test");
    }

}
