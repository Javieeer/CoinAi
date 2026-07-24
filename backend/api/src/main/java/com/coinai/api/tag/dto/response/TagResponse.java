package com.coinai.api.tag.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class TagResponse {

    private UUID id;

    private String name;

    private String color;

}