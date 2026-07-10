package com.coinai.api.account.service.impl;

import com.coinai.api.account.AccountType;
import com.coinai.api.account.dto.request.CreateAccountRequest;
import com.coinai.api.account.dto.response.AccountResponse;
import com.coinai.api.account.entity.Account;
import com.coinai.api.account.mapper.AccountMapper;
import com.coinai.api.account.repository.AccountRepository;
import com.coinai.api.common.exception.AccountAlreadyExistsException;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private AuthenticatedUserService authenticatedUserService;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void shouldCreateAccountSuccessfully() {

        // Arrange

        User user = User.builder()
                .id(UUID.randomUUID())
                .email("javier@test.com")
                .build();

        CreateAccountRequest request = new CreateAccountRequest();
        request.setName("Nequi");
        request.setType(AccountType.BANK);
        request.setCurrency("COP");

        Account account = Account.builder()
                .name("Nequi")
                .type(AccountType.BANK)
                .currency("COP")
                .user(user)
                .build();

        AccountResponse response = AccountResponse.builder()
                .name("Nequi")
                .type(AccountType.BANK)
                .currency("COP")
                .build();

        when(authenticatedUserService.getCurrentUser()).thenReturn(user);
        when(accountRepository.existsByUserIdAndNameIgnoreCase(user.getId(), "Nequi"))
                .thenReturn(false);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(accountMapper.toResponse(account)).thenReturn(response);

        // Act

        AccountResponse result = accountService.create(request);

        // Assert

        assertEquals("Nequi", result.getName());
        assertEquals(AccountType.BANK, result.getType());
        assertEquals("COP", result.getCurrency());

    }

    @Test
    void shouldThrowExceptionWhenAccountAlreadyExists() {

        // Arrange

        User user = User.builder()
                .id(UUID.randomUUID())
                .build();

        CreateAccountRequest request = new CreateAccountRequest();
        request.setName("Nequi");
        request.setType(AccountType.BANK);
        request.setCurrency("COP");

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(accountRepository.existsByUserIdAndNameIgnoreCase(
                user.getId(),
                "Nequi"))
                .thenReturn(true);

        // Act & Assert

        assertThrows(
                AccountAlreadyExistsException.class,
                () -> accountService.create(request)
        );

        verify(accountRepository, never()).save(any(Account.class));

    }

}