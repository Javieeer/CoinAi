package com.coinai.api.automation.learning.controller;

import com.coinai.api.automation.learning.dto.request.CreateMerchantRuleRequest;
import com.coinai.api.automation.learning.dto.response.MerchantRuleResponse;
import com.coinai.api.automation.learning.entity.MerchantRule;
import com.coinai.api.automation.learning.mapper.MerchantRuleMapper;
import com.coinai.api.automation.learning.service.MerchantLearningService;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/automation/merchant-rules")
@RequiredArgsConstructor
public class MerchantLearningController {

    private final MerchantLearningService merchantLearningService;
    private final MerchantRuleMapper merchantRuleMapper;
    private final AuthenticatedUserService authenticatedUserService;

    @PostMapping
    public MerchantRuleResponse create(
            @Valid
            @RequestBody CreateMerchantRuleRequest request
    ) {

        User user = authenticatedUserService.getCurrentUser();

        MerchantRule rule = merchantLearningService.learn(
                user.getId(),
                request
        );

        return merchantRuleMapper.toResponse(rule);

    }

}