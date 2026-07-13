package com.coinai.api.movement.service;

import com.coinai.api.movement.dto.request.CreateMovementRequest;
import com.coinai.api.movement.dto.request.UpdateMovementRequest;
import com.coinai.api.movement.dto.response.MovementResponse;

import java.util.List;
import java.util.UUID;

public interface MovementService {

    MovementResponse create(CreateMovementRequest request);

    List<MovementResponse> findAll();

    MovementResponse update(
            UUID id,
            UpdateMovementRequest request
    );

    void delete(UUID id);

}