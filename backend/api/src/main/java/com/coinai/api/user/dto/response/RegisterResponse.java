package com.coinai.api.user.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RegisterResponse {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

}