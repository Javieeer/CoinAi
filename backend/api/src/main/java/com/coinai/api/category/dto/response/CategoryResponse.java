package com.coinai.api.category.dto.response;

import com.coinai.api.movement.MovementType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class CategoryResponse {

    private UUID id;

    private String name;

    private String icon;

    private String color;

    private MovementType movementType;

    private boolean isDefault;

    private LocalDateTime createdAt;

}