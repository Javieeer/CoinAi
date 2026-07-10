package com.coinai.api.account.service.impl;

import com.coinai.api.account.dto.request.CreateAccountRequest;
import com.coinai.api.account.dto.response.AccountResponse;
import com.coinai.api.account.entity.Account;
import com.coinai.api.account.mapper.AccountMapper;
import com.coinai.api.account.repository.AccountRepository;
import com.coinai.api.account.service.AccountService;
import com.coinai.api.common.exception.AccountAlreadyExistsException;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.user.entity.User;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public AccountResponse create(CreateAccountRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        if (accountRepository.existsByUserIdAndNameIgnoreCase(user.getId(), request.getName())) {
            throw new AccountAlreadyExistsException();
        }

        Account account = Account.builder()
                .user(user)
                .name(request.getName())
                .type(request.getType())
                .currency(request.getCurrency())
                .archived(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        account = accountRepository.save(account);

        return accountMapper.toResponse(account);

    }

}