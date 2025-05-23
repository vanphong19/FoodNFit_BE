package com.vanphong.foodnfitbe.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Value("${cloudinary.cloud_name}")
    private String cloudName;

    @Value("${cloudinary.api_key}")
    private String apiKey;

    @Value("${cloudinary.api_secret}")
    private String apiSecret;
    @Bean
    public Cloudinary cloudinary() {
        final Map<String, String> configs = new HashMap<>();
        configs.put("cloud_name", cloudName);
        configs.put("api_key", apiKey);
        configs.put("api_secret", apiSecret);
        configs.put("secure", "true");
        return new Cloudinary(configs);
    }
}
