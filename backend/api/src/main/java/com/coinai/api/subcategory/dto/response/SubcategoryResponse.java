package com.coinai.api.subcategory.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class SubcategoryResponse {

    private UUID id;

    private UUID categoryId;

    private String name;

    private String icon;

    private String color;

    private boolean isDefault;

    private boolean archived;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}