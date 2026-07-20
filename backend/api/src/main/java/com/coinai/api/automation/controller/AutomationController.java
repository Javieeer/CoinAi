package com.coinai.api.automation.controller;

import com.coinai.api.automation.extraction.dto.MovementExtractionResult;
import com.coinai.api.automation.service.AutomationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/automation")
@RequiredArgsConstructor
public class AutomationController {

    private final AutomationService automationService;

    @PostMapping("/extract")
    public MovementExtractionResult extract(
            @RequestBody String email
    ) {

        UUID userId = UUID.fromString(
                "f881f51d-896f-478a-93dc-d236dcffc212"
        );

        return automationService.processEmail(
                userId,
                email
        );

    }

}