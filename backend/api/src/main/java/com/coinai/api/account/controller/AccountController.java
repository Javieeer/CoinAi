package com.coinai.api.account.controller;

import com.coinai.api.account.dto.request.CreateAccountRequest;
import com.coinai.api.account.dto.response.AccountResponse;
import com.coinai.api.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse create(
            @Valid @RequestBody CreateAccountRequest request
    ) {
        return accountService.create(request);
    }

}