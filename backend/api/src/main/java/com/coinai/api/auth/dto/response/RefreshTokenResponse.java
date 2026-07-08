package com.coinai.api.auth.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenResponse {

    private String accessToken;

    private String tokenType;

    private Long expiresIn;

}