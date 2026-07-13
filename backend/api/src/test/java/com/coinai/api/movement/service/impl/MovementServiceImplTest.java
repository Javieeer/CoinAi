package com.coinai.api.movement.service.impl;

import com.coinai.api.account.entity.Account;
import com.coinai.api.account.repository.AccountRepository;
import com.coinai.api.category.entity.Category;
import com.coinai.api.category.repository.CategoryRepository;
import com.coinai.api.movement.MovementSource;
import com.coinai.api.movement.MovementStatus;
import com.coinai.api.movement.MovementType;
import com.coinai.api.movement.MovementVisibility;
import com.coinai.api.movement.dto.request.CreateMovementRequest;
import com.coinai.api.movement.dto.request.UpdateMovementRequest;
import com.coinai.api.movement.dto.response.MovementResponse;
import com.coinai.api.movement.entity.Movement;
import com.coinai.api.movement.mapper.MovementMapper;
import com.coinai.api.movement.repository.MovementRepository;
import com.coinai.api.paymentMethods.entity.PaymentMethod;
import com.coinai.api.paymentMethods.repository.PaymentMethodRepository;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.subcategory.entity.Subcategory;
import com.coinai.api.subcategory.repository.SubcategoryRepository;
import com.coinai.api.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovementServiceImplTest {

    @Mock
    private MovementRepository movementRepository;

    @Mock
    private MovementMapper movementMapper;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @Mock
    private SubcategoryRepository subcategoryRepository;

    @Mock
    private AuthenticatedUserService authenticatedUserService;

    @InjectMocks
    private MovementServiceImpl movementService;

    @Test
    void shouldCreateMovementSuccessfully() {

        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        UUID accountId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        UUID paymentMethodId = UUID.randomUUID();
        UUID subcategoryId = UUID.randomUUID();

        Account account = Account.builder()
                .id(accountId)
                .user(user)
                .build();

        Category category = Category.builder()
                .id(categoryId)
                .user(user)
                .build();

        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(paymentMethodId)
                .user(user)
                .build();

        Subcategory subcategory = Subcategory.builder()
                .id(subcategoryId)
                .user(user)
                .build();

        CreateMovementRequest request = new CreateMovementRequest();

        request.setMovementType(MovementType.EXPENSE);
        request.setAmount(new BigDecimal("15000"));
        request.setDescription("Almuerzo");
        request.setMovementDate(LocalDateTime.now());

        request.setAccountId(accountId);
        request.setCategoryId(categoryId);
        request.setPaymentMethodId(paymentMethodId);
        request.setSubcategoryId(subcategoryId);

        request.setVisibility(MovementVisibility.PRIVATE);
        request.setStatus(MovementStatus.CONFIRMED);
        request.setSource(MovementSource.MANUAL);

        Movement movement = Movement.builder()
                .movementType(MovementType.EXPENSE)
                .amount(new BigDecimal("15000"))
                .description("Almuerzo")
                .account(account)
                .category(category)
                .paymentMethod(paymentMethod)
                .subcategory(subcategory)
                .user(user)
                .build();

        MovementResponse response = MovementResponse.builder()
                .movementType(MovementType.EXPENSE)
                .amount(new BigDecimal("15000"))
                .description("Almuerzo")
                .accountId(accountId)
                .categoryId(categoryId)
                .paymentMethodId(paymentMethodId)
                .subcategoryId(subcategoryId)
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(accountRepository.findByIdAndUserId(accountId, userId))
                .thenReturn(Optional.of(account));

        when(categoryRepository.findByIdAndUserId(categoryId, userId))
                .thenReturn(Optional.of(category));

        when(paymentMethodRepository.findByIdAndUserId(paymentMethodId, userId))
                .thenReturn(Optional.of(paymentMethod));

        when(subcategoryRepository.findByIdAndUserId(subcategoryId, userId))
                .thenReturn(Optional.of(subcategory));

        when(movementMapper.toEntity(request))
                .thenReturn(movement);

        when(movementRepository.save(any(Movement.class)))
                .thenReturn(movement);

        when(movementMapper.toResponse(movement))
                .thenReturn(response);

        MovementResponse result = movementService.create(request);

        assertEquals(MovementType.EXPENSE, result.getMovementType());
        assertEquals(new BigDecimal("15000"), result.getAmount());
        assertEquals("Almuerzo", result.getDescription());

    }

    @Test
    void shouldReturnAllMovements() {

        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        List<Movement> movements = List.of(
                Movement.builder()
                        .description("Movimiento 1")
                        .build(),
                Movement.builder()
                        .description("Movimiento 2")
                        .build()
        );

        List<MovementResponse> responses = List.of(
                MovementResponse.builder()
                        .description("Movimiento 1")
                        .build(),
                MovementResponse.builder()
                        .description("Movimiento 2")
                        .build()
        );

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(movementRepository.findByUserIdOrderByMovementDateDesc(userId))
                .thenReturn(movements);

        when(movementMapper.toResponseList(movements))
                .thenReturn(responses);

        List<MovementResponse> result = movementService.findAll();

        assertEquals(2, result.size());
        assertEquals("Movimiento 1", result.get(0).getDescription());
        assertEquals("Movimiento 2", result.get(1).getDescription());

    }

    @Test
    void shouldUpdateMovementSuccessfully() {

        UUID userId = UUID.randomUUID();
        UUID movementId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        UUID paymentMethodId = UUID.randomUUID();
        UUID subcategoryId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        Account account = Account.builder()
                .id(accountId)
                .user(user)
                .build();

        Category category = Category.builder()
                .id(categoryId)
                .user(user)
                .build();

        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(paymentMethodId)
                .user(user)
                .build();

        Subcategory subcategory = Subcategory.builder()
                .id(subcategoryId)
                .user(user)
                .build();

        Movement movement = Movement.builder()
                .id(movementId)
                .user(user)
                .build();

        UpdateMovementRequest request = new UpdateMovementRequest();

        request.setMovementType(MovementType.INCOME);
        request.setAmount(new BigDecimal("500000"));
        request.setDescription("Salario");
        request.setMovementDate(LocalDateTime.now());

        request.setAccountId(accountId);
        request.setCategoryId(categoryId);
        request.setPaymentMethodId(paymentMethodId);
        request.setSubcategoryId(subcategoryId);

        request.setVisibility(MovementVisibility.PRIVATE);
        request.setStatus(MovementStatus.CONFIRMED);
        request.setSource(MovementSource.MANUAL);

        MovementResponse response = MovementResponse.builder()
                .id(movementId)
                .movementType(MovementType.INCOME)
                .amount(new BigDecimal("500000"))
                .description("Salario")
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(movementRepository.findByIdAndUserId(movementId, userId))
                .thenReturn(Optional.of(movement));

        when(accountRepository.findByIdAndUserId(accountId, userId))
                .thenReturn(Optional.of(account));

        when(categoryRepository.findByIdAndUserId(categoryId, userId))
                .thenReturn(Optional.of(category));

        when(paymentMethodRepository.findByIdAndUserId(paymentMethodId, userId))
                .thenReturn(Optional.of(paymentMethod));

        when(subcategoryRepository.findByIdAndUserId(subcategoryId, userId))
                .thenReturn(Optional.of(subcategory));

        when(movementRepository.save(movement))
                .thenReturn(movement);

        when(movementMapper.toResponse(movement))
                .thenReturn(response);

        MovementResponse result = movementService.update(
                movementId,
                request
        );

        assertEquals(MovementType.INCOME, result.getMovementType());
        assertEquals(new BigDecimal("500000"), result.getAmount());
        assertEquals("Salario", result.getDescription());

    }

    @Test
    void shouldThrowWhenUpdatingNonExistingMovement() {

        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(movementRepository.findByIdAndUserId(any(), any()))
                .thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
                com.coinai.api.movement.exception.MovementNotFoundException.class,
                () -> movementService.update(
                        UUID.randomUUID(),
                        new UpdateMovementRequest()
                )
        );

    }

    @Test
    void shouldDeleteMovementSuccessfully() {

        UUID userId = UUID.randomUUID();
        UUID movementId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        Movement movement = Movement.builder()
                .id(movementId)
                .user(user)
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(movementRepository.findByIdAndUserId(movementId, userId))
                .thenReturn(Optional.of(movement));

        movementService.delete(movementId);

        org.mockito.Mockito.verify(movementRepository)
                .delete(movement);

    }

    @Test
    void shouldThrowWhenDeletingNonExistingMovement() {

        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(movementRepository.findByIdAndUserId(any(), any()))
                .thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
                com.coinai.api.movement.exception.MovementNotFoundException.class,
                () -> movementService.delete(UUID.randomUUID())
        );

    }

}