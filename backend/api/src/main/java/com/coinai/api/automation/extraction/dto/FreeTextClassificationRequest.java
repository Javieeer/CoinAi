package com.coinai.api.automation.extraction.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreeTextClassificationRequest {

    @NotBlank
    private String text;

}