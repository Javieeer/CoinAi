package com.coinai.api.user.controller;

import com.coinai.api.user.dto.request.UpdateProfileRequest;
import com.coinai.api.user.dto.response.UpdateProfileResponse;
import com.coinai.api.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public String me(Authentication authentication) {

        return authentication.getName();

    }

    @PutMapping("/profile")
    public UpdateProfileResponse updateProfile(
            @Valid @RequestBody UpdateProfileRequest request
    ) {

        return userService.updateProfile(request);

    }

}