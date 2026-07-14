package com.coinai.api.goal.service.impl;

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
import com.coinai.api.goal.service.GoalService;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final GoalMapper goalMapper;
    private final FamilyRepository familyRepository;
    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public GoalResponse create(CreateGoalRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        boolean exists;

        if (request.getFamilyId() == null) {
            exists = goalRepository.existsByUserIdAndFamilyIsNullAndNameIgnoreCase(
                    user.getId(),
                    request.getName()
            );
        } else {
            exists = goalRepository.existsByUserIdAndFamilyIdAndNameIgnoreCase(
                    user.getId(),
                    request.getFamilyId(),
                    request.getName()
            );
        }

        if (exists) {
            throw new GoalAlreadyExistsException();
        }

        Family family = null;

        if (request.getFamilyId() != null) {
            family = familyRepository.findById(request.getFamilyId())
                    .orElseThrow(FamilyNotFoundException::new);
        }

        Goal goal = goalMapper.toEntity(request);

        goal.setUser(user);
        goal.setFamily(family);
        goal.setCurrentAmount(BigDecimal.ZERO);
        goal.setStatus(GoalStatus.ACTIVE);
        goal.setCreatedAt(LocalDateTime.now());
        goal.setUpdatedAt(LocalDateTime.now());

        goal = goalRepository.save(goal);

        return enrichResponse(goalMapper.toResponse(goal), goal);

    }

    @Override
    public List<GoalResponse> findAll() {

        User user = authenticatedUserService.getCurrentUser();

        return goalRepository.findByUserIdOrderByTargetDateAsc(user.getId())
                .stream()
                .map(goal -> enrichResponse(
                        goalMapper.toResponse(goal),
                        goal
                ))
                .toList();

    }

    @Override
    public GoalResponse update(
            UUID id,
            UpdateGoalRequest request
    ) {

        User user = authenticatedUserService.getCurrentUser();

        Goal goal = goalRepository.findByIdAndUserId(
                id,
                user.getId()
        ).orElseThrow(GoalNotFoundException::new);

        boolean exists;

        if (request.getFamilyId() == null) {
            exists = goalRepository.existsByUserIdAndFamilyIsNullAndNameIgnoreCase(
                    user.getId(),
                    request.getName()
            );
        } else {
            exists = goalRepository.existsByUserIdAndFamilyIdAndNameIgnoreCase(
                    user.getId(),
                    request.getFamilyId(),
                    request.getName()
            );
        }

        boolean changed =
                !goal.getName().equalsIgnoreCase(request.getName())
                || (
                        (goal.getFamily() == null && request.getFamilyId() != null)
                        || (goal.getFamily() != null
                        && !goal.getFamily().getId().equals(request.getFamilyId()))
                );

        if (changed && exists) {
            throw new GoalAlreadyExistsException();
        }

        Family family = null;

        if (request.getFamilyId() != null) {
            family = familyRepository.findById(request.getFamilyId())
                    .orElseThrow(FamilyNotFoundException::new);
        }

        goal.setFamily(family);
        goal.setName(request.getName());
        goal.setDescription(request.getDescription());
        goal.setTargetAmount(request.getTargetAmount());
        goal.setCurrentAmount(request.getCurrentAmount());
        goal.setTargetDate(request.getTargetDate());

        if (goal.getCurrentAmount().compareTo(goal.getTargetAmount()) >= 0) {
            goal.setStatus(GoalStatus.COMPLETED);
        } else {
            goal.setStatus(GoalStatus.ACTIVE);
        }

        goal.setUpdatedAt(LocalDateTime.now());

        goal = goalRepository.save(goal);

        return enrichResponse(
                goalMapper.toResponse(goal),
                goal
        );

    }

    @Override
    public void delete(UUID id) {

        User user = authenticatedUserService.getCurrentUser();

        Goal goal = goalRepository.findByIdAndUserId(
                id,
                user.getId()
        ).orElseThrow(GoalNotFoundException::new);

        goalRepository.delete(goal);

    }

    private GoalResponse enrichResponse(
            GoalResponse response,
            Goal goal
    ) {

        BigDecimal remaining =
                goal.getTargetAmount().subtract(goal.getCurrentAmount());

        if (remaining.compareTo(BigDecimal.ZERO) < 0) {
            remaining = BigDecimal.ZERO;
        }

        int percentage = 0;

        if (goal.getTargetAmount().compareTo(BigDecimal.ZERO) > 0) {

            percentage = goal.getCurrentAmount()
                    .multiply(BigDecimal.valueOf(100))
                    .divide(
                            goal.getTargetAmount(),
                            0,
                            RoundingMode.HALF_UP
                    )
                    .intValue();

            percentage = Math.min(percentage, 100);

        }

        long months = ChronoUnit.MONTHS.between(
                LocalDate.now().withDayOfMonth(1),
                goal.getTargetDate().withDayOfMonth(1)
        );

        if (months < 1) {
            months = 1;
        }

        BigDecimal recommended = remaining.divide(
                BigDecimal.valueOf(months),
                2,
                RoundingMode.HALF_UP
        );

        response.setRemainingAmount(remaining);
        response.setProgressPercentage(percentage);
        response.setRemainingMonths((int) months);
        response.setRecommendedMonthlySaving(recommended);

        return response;

    }

}