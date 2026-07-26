package com.coinai.api.movement.service;

import com.coinai.api.movement.dto.request.CreateMovementRequest;
import com.coinai.api.movement.dto.request.UpdateMovementRequest;
import com.coinai.api.movement.dto.response.MovementResponse;
import com.coinai.api.movement.filter.MovementFilterRequest;

import java.util.List;
import java.util.UUID;

public interface MovementService {

    MovementResponse create(CreateMovementRequest request);

    List<MovementResponse> findAll(
        MovementFilterRequest filter
    );

    MovementResponse update(
            UUID id,
            UpdateMovementRequest request
    );

    void delete(UUID id);

}