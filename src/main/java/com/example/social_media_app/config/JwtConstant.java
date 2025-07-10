package com.example.social_media_app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public class JwtConstant {
    public static final String SECRET_KEY = "aW5zZWN1cmUtc2VjcmV0LXN0cmluZy1mb3ItanNvbg==";
    public static final String JWT_HEADER = "Authorization";
    public static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

}
