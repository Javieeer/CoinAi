package com.coinai.api.google.controller;

import com.coinai.api.google.service.GoogleOAuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;

import lombok.RequiredArgsConstructor;

import java.net.URI;

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

    @GetMapping("/connect")
    public ResponseEntity<Void> connect() {

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(
                        URI.create(
                                googleOAuthService.buildAuthorizationUrl()
                        )
                )
                .build();

    }
    
    @GetMapping("/callback")
    public String callback(
            @RequestParam String code
    ) throws Exception {

        GoogleTokenResponse token =
                googleOAuthService.exchangeCode(code);

        return token.getRefreshToken();
    }

}