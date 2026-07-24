package com.coinai.api.tag.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTagRequest {

    @NotBlank(message = "Tag name is required.")
    @Size(max = 100)
    private String name;

    @Size(max = 20)
    private String color;

}