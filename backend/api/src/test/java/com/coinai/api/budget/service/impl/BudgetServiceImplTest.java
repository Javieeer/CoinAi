package com.coinai.api.budget.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coinai.api.budget.dto.request.CreateBudgetRequest;
import com.coinai.api.budget.dto.request.UpdateBudgetRequest;
import com.coinai.api.budget.dto.response.BudgetResponse;
import com.coinai.api.budget.entity.Budget;
import com.coinai.api.budget.exception.BudgetAlreadyExistsException;
import com.coinai.api.budget.exception.BudgetNotFoundException;
import com.coinai.api.budget.mapper.BudgetMapper;
import com.coinai.api.budget.repository.BudgetRepository;
import com.coinai.api.category.entity.Category;
import com.coinai.api.category.exception.CategoryNotFoundException;
import com.coinai.api.category.repository.CategoryRepository;
import com.coinai.api.family.entity.Family;
import com.coinai.api.family.exception.FamilyNotFoundException;
import com.coinai.api.family.repository.FamilyRepository;
import com.coinai.api.movement.MovementType;
import com.coinai.api.movement.repository.MovementRepository;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.user.entity.User;

@ExtendWith(MockitoExtension.class)
class BudgetServiceImplTest {

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private BudgetMapper budgetMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private FamilyRepository familyRepository;

    @Mock
    private MovementRepository movementRepository;

    @Mock
    private AuthenticatedUserService authenticatedUserService;

    @InjectMocks
    private BudgetServiceImpl budgetService;

    private User user;
    private Category category;
    private Family family;

    @BeforeEach
    void setUp() {

        user = User.builder()
                .id(UUID.randomUUID())
                .build();

        category = Category.builder()
                .id(UUID.randomUUID())
                .user(user)
                .name("Food")
                .build();

        family = Family.builder()
                .id(UUID.randomUUID())
                .name("Home")
                .build();

    }

    @Test
    void shouldCreateBudgetSuccessfully() {

        CreateBudgetRequest request = new CreateBudgetRequest();
        request.setCategoryId(category.getId());
        request.setAmount(BigDecimal.valueOf(500000));
        request.setMonth((short) 7);
        request.setYear((short) 2026);

        Budget budget = Budget.builder()
                .user(user)
                .category(category)
                .amount(BigDecimal.valueOf(500000))
                .month((short) 7)
                .year((short) 2026)
                .build();

        BudgetResponse response = BudgetResponse.builder()
                .categoryId(category.getId())
                .amount(BigDecimal.valueOf(500000))
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(budgetRepository.existsByUserIdAndFamilyIsNullAndCategoryIdAndMonthAndYear(
                user.getId(),
                category.getId(),
                (short) 7,
                (short) 2026
        )).thenReturn(false);

        when(categoryRepository.findByIdAndUserId(
                category.getId(),
                user.getId()
        )).thenReturn(Optional.of(category));

        when(budgetMapper.toEntity(request))
                .thenReturn(budget);

        when(budgetRepository.save(any(Budget.class)))
                .thenReturn(budget);

        when(budgetMapper.toResponse(budget))
                .thenReturn(response);

        when(movementRepository.sumAmountByUserAndCategoryAndTypeAndDateBetween(
                any(),
                any(),
                any(MovementType.class),
                any(),
                any()
        )).thenReturn(BigDecimal.ZERO);

        BudgetResponse result = budgetService.create(request);

        assertEquals(BigDecimal.valueOf(500000), result.getAmount());
        assertEquals(BigDecimal.ZERO, result.getSpent());
        assertEquals(BigDecimal.valueOf(500000), result.getRemaining());
        assertEquals(0, result.getPercentage());

    }

    @Test
    void shouldThrowWhenBudgetAlreadyExists() {

        CreateBudgetRequest request = new CreateBudgetRequest();
        request.setCategoryId(category.getId());
        request.setMonth((short) 7);
        request.setYear((short) 2026);

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(budgetRepository.existsByUserIdAndFamilyIsNullAndCategoryIdAndMonthAndYear(
                user.getId(),
                category.getId(),
                (short) 7,
                (short) 2026
        )).thenReturn(true);

        assertThrows(
                BudgetAlreadyExistsException.class,
                () -> budgetService.create(request)
        );

        verify(budgetRepository, never()).save(any());

    }

    @Test
    void shouldThrowWhenCategoryNotFound() {

        CreateBudgetRequest request = new CreateBudgetRequest();
        request.setCategoryId(category.getId());
        request.setMonth((short) 7);
        request.setYear((short) 2026);

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(budgetRepository.existsByUserIdAndFamilyIsNullAndCategoryIdAndMonthAndYear(
                user.getId(),
                category.getId(),
                (short) 7,
                (short) 2026
        )).thenReturn(false);

        when(categoryRepository.findByIdAndUserId(
                category.getId(),
                user.getId()
        )).thenReturn(Optional.empty());

        assertThrows(
                CategoryNotFoundException.class,
                () -> budgetService.create(request)
        );

    }

    @Test
    void shouldThrowWhenFamilyNotFound() {

        CreateBudgetRequest request = new CreateBudgetRequest();
        request.setFamilyId(family.getId());
        request.setCategoryId(category.getId());
        request.setMonth((short) 7);
        request.setYear((short) 2026);

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(budgetRepository.existsByUserIdAndFamilyIdAndCategoryIdAndMonthAndYear(
                user.getId(),
                family.getId(),
                category.getId(),
                (short) 7,
                (short) 2026
        )).thenReturn(false);

        when(categoryRepository.findByIdAndUserId(
                category.getId(),
                user.getId()
        )).thenReturn(Optional.of(category));

        when(familyRepository.findById(family.getId()))
                .thenReturn(Optional.empty());

        assertThrows(
                FamilyNotFoundException.class,
                () -> budgetService.create(request)
        );

    }

    @Test
    void shouldFindAllBudgets() {

        Budget budget = Budget.builder()
                .user(user)
                .category(category)
                .amount(BigDecimal.valueOf(500000))
                .month((short) 7)
                .year((short) 2026)
                .build();

        BudgetResponse response = BudgetResponse.builder()
                .categoryId(category.getId())
                .amount(BigDecimal.valueOf(500000))
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(budgetRepository.findByUserIdAndMonthAndYear(
                user.getId(),
                (short) 7,
                (short) 2026
        )).thenReturn(List.of(budget));

        when(budgetMapper.toResponse(budget))
                .thenReturn(response);

        when(movementRepository.sumAmountByUserAndCategoryAndTypeAndDateBetween(
                any(),
                any(),
                any(MovementType.class),
                any(),
                any()
        )).thenReturn(BigDecimal.valueOf(120000));

        List<BudgetResponse> result = budgetService.findAll(
                (short) 7,
                (short) 2026
        );

        assertEquals(1, result.size());
        assertEquals(BigDecimal.valueOf(500000), result.getFirst().getAmount());
        assertEquals(BigDecimal.valueOf(120000), result.getFirst().getSpent());
        assertEquals(BigDecimal.valueOf(380000), result.getFirst().getRemaining());
        assertEquals(24, result.getFirst().getPercentage());

    }

    @Test
    void shouldUpdateBudgetSuccessfully() {

        UUID budgetId = UUID.randomUUID();

        Budget budget = Budget.builder()
                .id(budgetId)
                .user(user)
                .category(category)
                .amount(BigDecimal.valueOf(500000))
                .month((short) 7)
                .year((short) 2026)
                .build();

        UpdateBudgetRequest request = new UpdateBudgetRequest();
        request.setCategoryId(category.getId());
        request.setAmount(BigDecimal.valueOf(700000));
        request.setMonth((short) 7);
        request.setYear((short) 2026);

        BudgetResponse response = BudgetResponse.builder()
                .categoryId(category.getId())
                .amount(BigDecimal.valueOf(700000))
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(budgetRepository.findByIdAndUserId(
                budgetId,
                user.getId()
        )).thenReturn(Optional.of(budget));

        when(categoryRepository.findByIdAndUserId(
                category.getId(),
                user.getId()
        )).thenReturn(Optional.of(category));

        when(budgetRepository.save(any(Budget.class)))
                .thenReturn(budget);

        when(budgetMapper.toResponse(budget))
                .thenReturn(response);

        when(movementRepository.sumAmountByUserAndCategoryAndTypeAndDateBetween(
                any(),
                any(),
                any(MovementType.class),
                any(),
                any()
        )).thenReturn(BigDecimal.valueOf(100000));

        BudgetResponse result = budgetService.update(
                budgetId,
                request
        );

        assertEquals(BigDecimal.valueOf(700000), result.getAmount());
        assertEquals(BigDecimal.valueOf(100000), result.getSpent());
        assertEquals(BigDecimal.valueOf(600000), result.getRemaining());
        assertEquals(14, result.getPercentage());

    }

    @Test
    void shouldThrowWhenUpdatingNonExistingBudget() {

        UUID budgetId = UUID.randomUUID();

        UpdateBudgetRequest request = new UpdateBudgetRequest();
        request.setCategoryId(category.getId());
        request.setAmount(BigDecimal.valueOf(500000));
        request.setMonth((short) 7);
        request.setYear((short) 2026);

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(budgetRepository.findByIdAndUserId(
                budgetId,
                user.getId()
        )).thenReturn(Optional.empty());

        assertThrows(
                BudgetNotFoundException.class,
                () -> budgetService.update(budgetId, request)
        );

    }

    @Test
    void shouldThrowWhenUpdatingToExistingBudget() {

        UUID budgetId = UUID.randomUUID();

        Budget budget = Budget.builder()
                .id(budgetId)
                .user(user)
                .category(Category.builder()
                        .id(UUID.randomUUID())
                        .build())
                .month((short) 6)
                .year((short) 2026)
                .build();

        UpdateBudgetRequest request = new UpdateBudgetRequest();
        request.setCategoryId(category.getId());
        request.setAmount(BigDecimal.valueOf(500000));
        request.setMonth((short) 7);
        request.setYear((short) 2026);

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(budgetRepository.findByIdAndUserId(
                budgetId,
                user.getId()
        )).thenReturn(Optional.of(budget));

        when(budgetRepository.existsByUserIdAndFamilyIsNullAndCategoryIdAndMonthAndYear(
                user.getId(),
                category.getId(),
                (short) 7,
                (short) 2026
        )).thenReturn(true);

        assertThrows(
                BudgetAlreadyExistsException.class,
                () -> budgetService.update(budgetId, request)
        );

    }

    @Test
    void shouldThrowWhenUpdatingWithInvalidCategory() {

        UUID budgetId = UUID.randomUUID();

        Budget budget = Budget.builder()
                .id(budgetId)
                .user(user)
                .category(category)
                .month((short) 7)
                .year((short) 2026)
                .build();

        UpdateBudgetRequest request = new UpdateBudgetRequest();
        request.setCategoryId(category.getId());
        request.setAmount(BigDecimal.valueOf(500000));
        request.setMonth((short) 7);
        request.setYear((short) 2026);

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(budgetRepository.findByIdAndUserId(
                budgetId,
                user.getId()
        )).thenReturn(Optional.of(budget));

        when(categoryRepository.findByIdAndUserId(
                category.getId(),
                user.getId()
        )).thenReturn(Optional.empty());

        assertThrows(
                CategoryNotFoundException.class,
                () -> budgetService.update(budgetId, request)
        );

    }

    @Test
    void shouldThrowWhenUpdatingWithInvalidFamily() {

        UUID budgetId = UUID.randomUUID();

        Budget budget = Budget.builder()
                .id(budgetId)
                .user(user)
                .category(category)
                .month((short) 7)
                .year((short) 2026)
                .build();

        UpdateBudgetRequest request = new UpdateBudgetRequest();
        request.setFamilyId(family.getId());
        request.setCategoryId(category.getId());
        request.setAmount(BigDecimal.valueOf(500000));
        request.setMonth((short) 7);
        request.setYear((short) 2026);

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(budgetRepository.findByIdAndUserId(
                budgetId,
                user.getId()
        )).thenReturn(Optional.of(budget));

        when(budgetRepository.existsByUserIdAndFamilyIdAndCategoryIdAndMonthAndYear(
                user.getId(),
                family.getId(),
                category.getId(),
                (short) 7,
                (short) 2026
        )).thenReturn(false);

        when(categoryRepository.findByIdAndUserId(
                category.getId(),
                user.getId()
        )).thenReturn(Optional.of(category));

        when(familyRepository.findById(family.getId()))
                .thenReturn(Optional.empty());

        assertThrows(
                FamilyNotFoundException.class,
                () -> budgetService.update(budgetId, request)
        );

    }

    @Test
    void shouldDeleteBudgetSuccessfully() {

        UUID budgetId = UUID.randomUUID();

        Budget budget = Budget.builder()
                .id(budgetId)
                .user(user)
                .category(category)
                .amount(BigDecimal.valueOf(500000))
                .month((short) 7)
                .year((short) 2026)
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(budgetRepository.findByIdAndUserId(
                budgetId,
                user.getId()
        )).thenReturn(Optional.of(budget));

        budgetService.delete(budgetId);

        verify(budgetRepository).delete(budget);

    }

    @Test
    void shouldThrowWhenDeletingNonExistingBudget() {

        UUID budgetId = UUID.randomUUID();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(budgetRepository.findByIdAndUserId(
                budgetId,
                user.getId()
        )).thenReturn(Optional.empty());

        assertThrows(
                BudgetNotFoundException.class,
                () -> budgetService.delete(budgetId)
        );

        verify(budgetRepository, never()).delete(any());

    }

}