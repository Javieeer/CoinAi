package com.coinai.api.goal.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
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

import com.coinai.api.family.entity.Family;
import com.coinai.api.family.exception.FamilyNotFoundException;
import com.coinai.api.family.repository.FamilyRepository;
import com.coinai.api.goal.GoalStatus;
import com.coinai.api.goal.dto.request.CreateGoalRequest;
import com.coinai.api.goal.dto.request.UpdateGoalRequest;
import com.coinai.api.goal.dto.response.GoalResponse;
import com.coinai.api.goal.entity.Goal;
import com.coinai.api.goal.exception.GoalAlreadyExistsException;
import com.coinai.api.goal.exception.GoalNotFoundException;
import com.coinai.api.goal.mapper.GoalMapper;
import com.coinai.api.goal.repository.GoalRepository;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.user.entity.User;

@ExtendWith(MockitoExtension.class)
class GoalServiceImplTest {

    @Mock
    private GoalRepository goalRepository;

    @Mock
    private GoalMapper goalMapper;

    @Mock
    private FamilyRepository familyRepository;

    @Mock
    private AuthenticatedUserService authenticatedUserService;

    @InjectMocks
    private GoalServiceImpl goalService;

    private User user;
    private Family family;

    @BeforeEach
    void setUp() {

        user = User.builder()
                .id(UUID.randomUUID())
                .build();

        family = Family.builder()
                .id(UUID.randomUUID())
                .name("Home")
                .build();

    }

    @Test
    void shouldCreateGoalSuccessfully() {

        CreateGoalRequest request = new CreateGoalRequest();
        request.setName("Car");
        request.setDescription("Save for a car");
        request.setTargetAmount(BigDecimal.valueOf(20000000));
        request.setTargetDate(LocalDate.now().plusMonths(10));

        Goal goal = Goal.builder()
                .user(user)
                .name("Car")
                .description("Save for a car")
                .targetAmount(BigDecimal.valueOf(20000000))
                .currentAmount(BigDecimal.ZERO)
                .status(GoalStatus.ACTIVE)
                .targetDate(request.getTargetDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        GoalResponse response = GoalResponse.builder()
                .name("Car")
                .targetAmount(BigDecimal.valueOf(20000000))
                .currentAmount(BigDecimal.ZERO)
                .status(GoalStatus.ACTIVE)
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(goalRepository.existsByUserIdAndFamilyIsNullAndNameIgnoreCase(
                user.getId(),
                "Car"
        )).thenReturn(false);

        when(goalMapper.toEntity(request))
                .thenReturn(goal);

        when(goalRepository.save(any(Goal.class)))
                .thenReturn(goal);

        when(goalMapper.toResponse(goal))
                .thenReturn(response);

        GoalResponse result = goalService.create(request);

        assertEquals("Car", result.getName());
        assertEquals(GoalStatus.ACTIVE, result.getStatus());

    }

    @Test
        void shouldThrowWhenGoalAlreadyExists() {

        CreateGoalRequest request = new CreateGoalRequest();
        request.setName("Car");

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(goalRepository.existsByUserIdAndFamilyIsNullAndNameIgnoreCase(
                user.getId(),
                "Car"
        )).thenReturn(true);

        assertThrows(
                GoalAlreadyExistsException.class,
                () -> goalService.create(request)
        );

        verify(goalRepository, never()).save(any());

        }

    @Test
    void shouldThrowWhenFamilyNotFound() {

        CreateGoalRequest request = new CreateGoalRequest();
        request.setName("Car");
        request.setFamilyId(UUID.randomUUID());

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(goalRepository.existsByUserIdAndFamilyIdAndNameIgnoreCase(
                user.getId(),
                request.getFamilyId(),
                "Car"
        )).thenReturn(false);

        when(familyRepository.findById(request.getFamilyId()))
                .thenReturn(Optional.empty());

        assertThrows(
                FamilyNotFoundException.class,
                () -> goalService.create(request)
        );

    }

    @Test
    void shouldFindAllGoals() {

        Goal goal = Goal.builder()
                .user(user)
                .name("Car")
                .targetAmount(BigDecimal.valueOf(1000))
                .currentAmount(BigDecimal.valueOf(200))
                .targetDate(LocalDate.now().plusMonths(8))
                .build();

        GoalResponse response = GoalResponse.builder()
                .name("Car")
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(goalRepository.findByUserIdOrderByTargetDateAsc(user.getId()))
                .thenReturn(List.of(goal));

        when(goalMapper.toResponse(goal))
                .thenReturn(response);

        List<GoalResponse> result = goalService.findAll();

        assertEquals(1, result.size());

    }

    @Test
    void shouldUpdateGoalSuccessfully() {

        UUID id = UUID.randomUUID();

        UpdateGoalRequest request = new UpdateGoalRequest();
        request.setName("New Car");
        request.setDescription("Updated");
        request.setTargetAmount(BigDecimal.valueOf(1500));
        request.setCurrentAmount(BigDecimal.valueOf(300));
        request.setTargetDate(LocalDate.now().plusMonths(12));

        Goal goal = Goal.builder()
                .id(id)
                .user(user)
                .name("Car")
                .targetAmount(BigDecimal.valueOf(1000))
                .currentAmount(BigDecimal.ZERO)
                .targetDate(LocalDate.now().plusMonths(10))
                .status(GoalStatus.ACTIVE)
                .build();

        GoalResponse response = GoalResponse.builder()
                .name("New Car")
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(goalRepository.findByIdAndUserId(id, user.getId()))
                .thenReturn(Optional.of(goal));

        when(goalRepository.existsByUserIdAndFamilyIsNullAndNameIgnoreCase(
                user.getId(),
                "New Car"
        )).thenReturn(false);

        when(goalRepository.save(any(Goal.class)))
                .thenReturn(goal);

        when(goalMapper.toResponse(goal))
                .thenReturn(response);

        GoalResponse result = goalService.update(id, request);

        assertEquals("New Car", result.getName());

    }

    @Test
    void shouldThrowWhenGoalNotFoundOnUpdate() {

        UUID id = UUID.randomUUID();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(goalRepository.findByIdAndUserId(id, user.getId()))
                .thenReturn(Optional.empty());

        assertThrows(
                GoalNotFoundException.class,
                () -> goalService.update(id, new UpdateGoalRequest())
        );

    }

    @Test
    void shouldDeleteGoalSuccessfully() {

        UUID id = UUID.randomUUID();

        Goal goal = Goal.builder()
                .id(id)
                .user(user)
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(goalRepository.findByIdAndUserId(id, user.getId()))
                .thenReturn(Optional.of(goal));

        goalService.delete(id);

        verify(goalRepository).delete(goal);

    }

    @Test
    void shouldThrowWhenGoalNotFoundOnDelete() {

        UUID id = UUID.randomUUID();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(goalRepository.findByIdAndUserId(id, user.getId()))
                .thenReturn(Optional.empty());

        assertThrows(
                GoalNotFoundException.class,
                () -> goalService.delete(id)
        );

    }

}