package com.coinai.api.google.controller;

import com.coinai.api.google.service.GoogleCredentialService;
import com.coinai.api.google.service.GoogleOAuthService;
import com.coinai.api.google.state.entity.OAuthState;
import com.coinai.api.google.state.service.OAuthStateService;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.coinai.api.user.entity.User;

import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/v1/google")
@RequiredArgsConstructor
public class GoogleController {

    private final GoogleOAuthService googleOAuthService;
    private final GoogleCredentialService googleCredentialService;
    private final AuthenticatedUserService authenticatedUserService;
    private final OAuthStateService oauthStateService;

    @GetMapping("/connect")
    public ResponseEntity<Void> connect() {

        User user = authenticatedUserService.getCurrentUser();

        OAuthState oauthState =
                oauthStateService.create(user.getId());

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(
                        URI.create(
                                googleOAuthService.buildAuthorizationUrl(
                                        oauthState.getState()
                                )
                        )
                )
                .build();

    }
    
    @GetMapping("/callback")
    public String callback(
        @RequestParam String code,
        @RequestParam String state
    ) throws Exception {

        OAuthState oauthState =
                oauthStateService.get(state);

        GoogleTokenResponse token =
                googleOAuthService.exchangeCode(code);

        googleCredentialService.save(
                oauthState.getUser().getId(),
                token
        );

        oauthStateService.delete(state);

        return "Cuenta de Gmail conectada correctamente.";

    }

}