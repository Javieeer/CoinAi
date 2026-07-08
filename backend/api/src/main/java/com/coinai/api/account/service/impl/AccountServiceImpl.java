package com.coinai.api.account.service.impl;

import com.coinai.api.account.dto.request.CreateAccountRequest;
import com.coinai.api.account.dto.response.AccountResponse;
import com.coinai.api.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    @Override
    public AccountResponse create(CreateAccountRequest request) {

        return null;

    }

}