package com.coinai.api.goal.mapper;

import com.coinai.api.goal.dto.request.CreateGoalRequest;
import com.coinai.api.goal.dto.response.GoalResponse;
import com.coinai.api.goal.entity.Goal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GoalMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "family", ignore = true)
    @Mapping(target = "currentAmount", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Goal toEntity(CreateGoalRequest request);

    @Mapping(target = "familyId", source = "family.id")
    @Mapping(target = "remainingAmount", ignore = true)
    @Mapping(target = "progressPercentage", ignore = true)
    @Mapping(target = "remainingMonths", ignore = true)
    @Mapping(target = "recommendedMonthlySaving", ignore = true)
    GoalResponse toResponse(Goal goal);

    List<GoalResponse> toResponseList(List<Goal> goals);

}