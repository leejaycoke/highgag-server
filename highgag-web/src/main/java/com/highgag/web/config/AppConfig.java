package com.highgag.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    private Auth auth;

    private Scrypt scrypt;

    @Data
    public static class Auth {
        private String secretKey;
        private int expireSeconds;
    }

    @Data
    public static class Scrypt {
        private int n;
        private int r;
        private int p;
    }

}
