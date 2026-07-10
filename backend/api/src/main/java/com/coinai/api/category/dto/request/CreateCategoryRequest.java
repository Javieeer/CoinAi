package com.coinai.api.category.dto.request;

import com.coinai.api.movement.MovementType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryRequest {

    @NotBlank(message = "Category name is required.")
    @Size(max = 100, message = "Category name cannot exceed 100 characters.")
    private String name;

    @Size(max = 100, message = "Icon cannot exceed 100 characters.")
    private String icon;

    @Size(max = 20, message = "Color cannot exceed 20 characters.")
    private String color;

    @NotNull(message = "Movement type is required.")
    private MovementType movementType;

}