package com.coinai.api.goal.service;

import com.coinai.api.goal.dto.request.CreateGoalRequest;
import com.coinai.api.goal.dto.request.UpdateGoalRequest;
import com.coinai.api.goal.dto.response.GoalResponse;

import java.util.List;
import java.util.UUID;

public interface GoalService {

    GoalResponse create(CreateGoalRequest request);

    List<GoalResponse> findAll();

    GoalResponse update(
            UUID id,
            UpdateGoalRequest request
    );

    void delete(UUID id);

}