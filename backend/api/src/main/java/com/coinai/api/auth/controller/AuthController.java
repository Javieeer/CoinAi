package com.coinai.api.auth.controller;

import com.coinai.api.auth.dto.request.LoginRequest;
import com.coinai.api.auth.dto.response.LoginResponse;
import com.coinai.api.auth.service.AuthService;
import com.coinai.api.user.dto.request.RegisterRequest;
import com.coinai.api.user.dto.response.RegisterResponse;
import com.coinai.api.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(
            @Valid @RequestBody RegisterRequest request
    ) {

        return userService.register(request);

    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }
}