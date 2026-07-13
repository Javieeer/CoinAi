package com.coinai.api.subcategory.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateSubcategoryRequest {

    @NotNull
    private UUID categoryId;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String icon;

    @Size(max = 20)
    private String color;

}