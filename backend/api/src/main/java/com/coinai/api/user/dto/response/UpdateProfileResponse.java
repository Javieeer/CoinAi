package com.coinai.api.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateProfileResponse {

    private String firstName;

    private String lastName;

    private String email;

    private String profilePictureUrl;

    private String preferredCurrency;

    private String timezone;

}