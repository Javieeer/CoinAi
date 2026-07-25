package com.coinai.api.cloudinary;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(CloudinaryProperties.class)
public class CloudinaryConfig {

    private final CloudinaryProperties properties;

    @Bean
    public Cloudinary cloudinary() {

        return new Cloudinary(
                java.util.Map.of(
                        "cloud_name", properties.getCloudName(),
                        "api_key", properties.getApiKey(),
                        "api_secret", properties.getApiSecret(),
                        "secure", true
                )
        );

    }

}