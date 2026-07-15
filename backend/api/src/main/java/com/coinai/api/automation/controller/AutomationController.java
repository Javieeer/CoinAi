package com.coinai.api.automation.controller;

import com.coinai.api.automation.extraction.dto.MovementExtractionResult;
import com.coinai.api.automation.extraction.service.ExtractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/automation")
@RequiredArgsConstructor
public class AutomationController {

    private final ExtractionService extractionService;

    @PostMapping("/extract")
    public MovementExtractionResult extract(@RequestBody String email) {

        return extractionService.extract(email);

    }

}