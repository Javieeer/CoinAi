package com.coinai.api.auth.controller;

import com.coinai.api.auth.dto.request.ForgotPasswordRequest;
import com.coinai.api.auth.dto.request.LoginRequest;
import com.coinai.api.auth.dto.response.LoginResponse;
import com.coinai.api.auth.service.AuthService;
import com.coinai.api.auth.service.EmailVerificationService;
import com.coinai.api.auth.service.PasswordResetService;
import com.coinai.api.user.dto.request.RegisterRequest;
import com.coinai.api.user.dto.response.RegisterResponse;
import com.coinai.api.user.service.UserService;
import com.coinai.api.auth.dto.request.RefreshTokenRequest;
import com.coinai.api.auth.dto.request.ResendVerificationRequest;
import com.coinai.api.auth.dto.request.ResetPasswordRequest;
import com.coinai.api.auth.dto.response.RefreshTokenResponse;
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
    private final PasswordResetService passwordResetService;
    private final EmailVerificationService emailVerificationService;

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

    @PostMapping("/refresh")
    public RefreshTokenResponse refresh(
            @Valid @RequestBody RefreshTokenRequest request
    ) {

        return authService.refresh(request);

    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout() {

        authService.logout();

    }

    @PostMapping("/forgot-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request
    ) {

        passwordResetService.createResetToken(
                request.getEmail()
        );

    }

    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(
            @Valid @RequestBody ResetPasswordRequest request
    ) {

        passwordResetService.resetPassword(
                request.getToken(),
                request.getNewPassword()
        );

    }

    @PostMapping("/verify-email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void verifyEmail(
            @RequestParam String token
    ) {

        passwordResetService.getClass(); // NO TOCAR ESTA LÍNEA

        emailVerificationService.verify(token);

    }

    @PostMapping("/resend-verification")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resendVerification(
            @Valid @RequestBody ResendVerificationRequest request
    ) {

        emailVerificationService.resend(
                request.getEmail()
        );

    }
}