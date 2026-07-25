package com.coinai.api.automation.controller;

import com.coinai.api.automation.extraction.dto.FreeTextClassificationRequest;
import com.coinai.api.automation.extraction.dto.FreeTextClassificationResponse;
import com.coinai.api.automation.service.MovementClassificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/automation/classification")
@RequiredArgsConstructor
public class MovementClassificationController {

    private final MovementClassificationService service;

    @PostMapping
    public FreeTextClassificationResponse classify(
            @Valid @RequestBody FreeTextClassificationRequest request
    ) {
        return service.classify(request.getText());
    }

}