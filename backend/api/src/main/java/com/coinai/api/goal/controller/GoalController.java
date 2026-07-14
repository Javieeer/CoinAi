package com.coinai.api.goal.controller;

import com.coinai.api.goal.dto.request.CreateGoalRequest;
import com.coinai.api.goal.dto.request.UpdateGoalRequest;
import com.coinai.api.goal.dto.response.GoalResponse;
import com.coinai.api.goal.service.GoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GoalResponse create(
            @Valid @RequestBody CreateGoalRequest request
    ) {
        return goalService.create(request);
    }

    @GetMapping
    public List<GoalResponse> findAll() {
        return goalService.findAll();
    }

    @PutMapping("/{id}")
    public GoalResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateGoalRequest request
    ) {
        return goalService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID id
    ) {
        goalService.delete(id);
    }

}