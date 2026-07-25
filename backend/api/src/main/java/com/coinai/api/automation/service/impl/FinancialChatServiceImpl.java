package com.coinai.api.automation.service.impl;

import com.coinai.api.user.entity.User;
import com.coinai.api.category.entity.Category;
import com.coinai.api.automation.extraction.dto.FinancialQuestionResult;
import com.coinai.api.automation.openai.AIClient;
import com.coinai.api.automation.parser.AIResponseParser;
import com.coinai.api.automation.prompt.FinancialChatPrompt;
import com.coinai.api.automation.service.FinancialChatService;
import com.coinai.api.automation.service.PeriodResolver;
import com.coinai.api.category.repository.CategoryRepository;
import com.coinai.api.movement.MovementType;
import com.coinai.api.movement.repository.MovementRepository;
import com.coinai.api.security.service.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinancialChatServiceImpl
        implements FinancialChatService {

    private final AIClient aiClient;
    private final FinancialChatPrompt prompt;
    private final AIResponseParser parser;
    private final MovementRepository movementRepository;
    private final CategoryRepository categoryRepository;
    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public String ask(String question) {

        String aiResponse = aiClient.chat(
                prompt.build(question)
        );

        FinancialQuestionResult result =
                parser.parse(
                        aiResponse,
                        FinancialQuestionResult.class
                );

        if (!"CATEGORY_EXPENSE".equals(result.getIntent())) {
                return "Por el momento esa consulta aún no está soportada.";
        }

        User user = authenticatedUserService.getCurrentUser();

        Category category = categoryRepository
                .findByUserIdAndNameIgnoreCase(
                        user.getId(),
                        result.getCategory()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Categoría no encontrada: "
                                        + result.getCategory()
                        )
                );

        PeriodResolver.DateRange range =
                PeriodResolver.resolve(
                        result.getPeriod()
                );

        BigDecimal spent =
                movementRepository
                        .sumAmountByUserAndCategoryAndTypeAndDateBetween(
                                user.getId(),
                                category.getId(),
                                MovementType.EXPENSE,
                                range.getStart(),
                                range.getEnd()
                        );

        String context = """
                Datos financieros:

                Categoría: %s

                Total gastado: %s COP

                Responde al usuario de forma amable,
                breve y natural.
                """
                .formatted(
                        category.getName(),
                        spent
                );

        return aiClient.chat(
                context +
                "\n\nPregunta:\n" +
                question
        );

    }

}