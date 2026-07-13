package com.coinai.api.account.service.impl;

import com.coinai.api.account.AccountType;
import com.coinai.api.account.dto.request.CreateAccountRequest;
import com.coinai.api.account.dto.request.UpdateAccountRequest;
import com.coinai.api.account.dto.response.AccountResponse;
import com.coinai.api.account.entity.Account;
import com.coinai.api.account.exception.AccountAlreadyExistsException;
import com.coinai.api.account.exception.AccountNotFoundException;
import com.coinai.api.account.mapper.AccountMapper;
import com.coinai.api.account.repository.AccountRepository;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

    @Test
        void shouldReturnAllAccounts() {

                UUID userId = UUID.randomUUID();

                User user = User.builder()
                        .id(userId)
                        .build();

                List<Account> accounts = List.of(
                        Account.builder()
                                .name("Banco")
                                .build(),
                        Account.builder()
                                .name("Nequi")
                                .build()
                );

                List<AccountResponse> responses = List.of(
                        AccountResponse.builder().name("Banco").build(),
                        AccountResponse.builder().name("Nequi").build()
                );

                when(authenticatedUserService.getCurrentUser()).thenReturn(user);

                when(accountRepository.findByUserIdAndArchivedFalseOrderByNameAsc(userId))
                        .thenReturn(accounts);

                when(accountMapper.toResponseList(accounts))
                        .thenReturn(responses);

                List<AccountResponse> result = accountService.findAll();

                assertEquals(2, result.size());
                assertEquals("Banco", result.get(0).getName());
                assertEquals("Nequi", result.get(1).getName());

        }

    @Test
        void shouldUpdateAccountSuccessfully() {

                UUID userId = UUID.randomUUID();
                UUID accountId = UUID.randomUUID();

                User user = User.builder()
                        .id(userId)
                        .build();

                Account account = Account.builder()
                        .id(accountId)
                        .name("Nequi")
                        .type(AccountType.BANK)
                        .currency("COP")
                        .user(user)
                        .build();

                UpdateAccountRequest request = new UpdateAccountRequest();
                request.setName("Bancolombia");
                request.setType(AccountType.BANK);
                request.setCurrency("USD");

                AccountResponse response = AccountResponse.builder()
                        .id(accountId)
                        .name("Bancolombia")
                        .type(AccountType.BANK)
                        .currency("USD")
                        .build();

                when(authenticatedUserService.getCurrentUser()).thenReturn(user);

                when(accountRepository.findByIdAndUserId(accountId, userId))
                        .thenReturn(Optional.of(account));

                when(accountRepository.save(account))
                        .thenReturn(account);

                when(accountMapper.toResponse(account))
                        .thenReturn(response);

                AccountResponse result = accountService.update(accountId, request);

                assertEquals("Bancolombia", result.getName());
                assertEquals("USD", result.getCurrency());

        }

    @Test
        void shouldThrowWhenUpdatingNonExistingAccount() {

                UUID userId = UUID.randomUUID();

                User user = User.builder()
                        .id(userId)
                        .build();

                when(authenticatedUserService.getCurrentUser()).thenReturn(user);

                when(accountRepository.findByIdAndUserId(any(), eq(userId)))
                        .thenReturn(Optional.empty());

                assertThrows(
                        AccountNotFoundException.class,
                        () -> accountService.update(
                                UUID.randomUUID(),
                                new UpdateAccountRequest()
                        )
                );

        }

    @Test
        void shouldArchiveAccountSuccessfully() {

                UUID userId = UUID.randomUUID();
                UUID accountId = UUID.randomUUID();

                User user = User.builder()
                        .id(userId)
                        .build();

                Account account = Account.builder()
                        .id(accountId)
                        .archived(false)
                        .user(user)
                        .build();

                when(authenticatedUserService.getCurrentUser()).thenReturn(user);

                when(accountRepository.findByIdAndUserId(accountId, userId))
                        .thenReturn(Optional.of(account));

                accountService.delete(accountId);

                assertTrue(account.isArchived());

                verify(accountRepository).save(account);

        }
        
    @Test
        void shouldThrowWhenDeletingNonExistingAccount() {

                UUID userId = UUID.randomUUID();

                User user = User.builder()
                        .id(userId)
                        .build();

                when(authenticatedUserService.getCurrentUser()).thenReturn(user);

                when(accountRepository.findByIdAndUserId(any(), eq(userId)))
                        .thenReturn(Optional.empty());

                assertThrows(
                        AccountNotFoundException.class,
                        () -> accountService.delete(UUID.randomUUID())
                );

        }


        
}