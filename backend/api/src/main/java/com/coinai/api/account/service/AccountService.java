package com.coinai.api.account.service;

import com.coinai.api.account.dto.request.CreateAccountRequest;
import com.coinai.api.account.dto.response.AccountResponse;

public interface AccountService {

    AccountResponse create(CreateAccountRequest request);

}