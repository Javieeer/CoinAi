package com.coinai.api.category.dto.request;

import com.coinai.api.movement.MovementType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCategoryRequest {

    @NotBlank(message = "Category name is required.")
    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String icon;

    @Size(max = 20)
    private String color;

    @NotNull
    private MovementType movementType;

}