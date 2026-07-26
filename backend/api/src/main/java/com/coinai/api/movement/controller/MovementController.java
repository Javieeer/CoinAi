package com.coinai.api.movement.controller;

import com.coinai.api.movement.MovementType;
import com.coinai.api.movement.dto.request.CreateMovementRequest;
import com.coinai.api.movement.dto.request.UpdateMovementRequest;
import com.coinai.api.movement.dto.response.MovementResponse;
import com.coinai.api.movement.filter.MovementFilterRequest;
import com.coinai.api.movement.service.MovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public List<MovementResponse> findAll(

            @RequestParam(required = false)
            MovementType movementType,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate,

            @RequestParam(required = false)
            UUID categoryId,

            @RequestParam(required = false)
            UUID paymentMethodId,

            @RequestParam(required = false)
            UUID tagId

    ) {

        MovementFilterRequest filter = MovementFilterRequest
                .builder()
                .movementType(movementType)
                .startDate(startDate)
                .endDate(endDate)
                .categoryId(categoryId)
                .paymentMethodId(paymentMethodId)
                .tagId(tagId)
                .build();

        return movementService.findAll(filter);

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