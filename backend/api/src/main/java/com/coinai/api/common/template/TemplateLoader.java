package com.coinai.api.common.template;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class TemplateLoader {

    @SneakyThrows
    public String load(String file) {

        ClassPathResource resource =
                new ClassPathResource("templates/" + file);

        return new String(
                resource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );

    }

}