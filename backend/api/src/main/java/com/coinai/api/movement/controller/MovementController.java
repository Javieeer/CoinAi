package com.coinai.api.movement.controller;

import com.coinai.api.movement.dto.request.CreateMovementRequest;
import com.coinai.api.movement.dto.request.UpdateMovementRequest;
import com.coinai.api.movement.dto.response.MovementResponse;
import com.coinai.api.movement.service.MovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/movements")
@RequiredArgsConstructor
public class MovementController {

    private final MovementService movementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovementResponse create(
            @Valid @RequestBody CreateMovementRequest request
    ) {

        return movementService.create(request);

    }

    @GetMapping
    public List<MovementResponse> findAll() {

        return movementService.findAll();

    }

    @PutMapping("/{id}")
    public MovementResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateMovementRequest request
    ) {

        return movementService.update(id, request);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID id
    ) {

        movementService.delete(id);

    }

}