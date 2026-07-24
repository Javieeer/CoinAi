package com.coinai.api.account.service;

import com.coinai.api.account.dto.request.CreateAccountRequest;
import com.coinai.api.account.dto.request.UpdateAccountRequest;
import com.coinai.api.account.dto.response.AccountBalanceResponse;
import com.coinai.api.account.dto.response.AccountResponse;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountResponse create(CreateAccountRequest request);

    List<AccountResponse> findAll();

    AccountResponse update(UUID id, UpdateAccountRequest request);

    void delete(UUID id);

    AccountBalanceResponse getBalance(UUID accountId);
}