package com.coinai.api.automation.parser.bank;

import com.coinai.api.automation.movement.dto.MovementDraft;
import com.coinai.api.automation.parser.BankType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BancolombiaParserImpl implements BancolombiaParser {

    private static final Pattern AMOUNT_PATTERN =
        Pattern.compile("\\$([\\d.,]+)");

    private static final Pattern SOURCE_ACCOUNT_PATTERN =
            Pattern.compile("cuenta\\s+(\\d{4})", Pattern.CASE_INSENSITIVE);

    private static final Pattern DESTINATION_ACCOUNT_PATTERN =
            Pattern.compile("cuenta\\s+\\*(\\d+)", Pattern.CASE_INSENSITIVE);

    private static final Pattern DATE_PATTERN =
            Pattern.compile("(\\d{2}/\\d{2}/\\d{4})");

    private static final Pattern TIME_PATTERN =
            Pattern.compile("(\\d{2}:\\d{2}(?::\\d{2})?)");

    @Override
    public boolean supports(BankType bankType) {
        return bankType == BankType.BANCOLOMBIA;
    }

    @Override
    public MovementDraft parse(String body) {

        BigDecimal amount = extractAmount(body);

        String sourceAccount = extractSourceAccount(body);

        String destinationAccount = extractDestinationAccount(body);

        LocalDateTime occurredAt = extractOccurredAt(body);

        if (!isMovement(body)) {
            return null;
        }

        return MovementDraft.builder()
            .amount(amount)
            .sourceAccount(sourceAccount)
            .destinationAccount(destinationAccount)
            .occurredAt(occurredAt)
            .description(body)
            .bankType(BankType.BANCOLOMBIA)
            .rawBody(body)
            .build();

    }

    private BigDecimal extractAmount(String body) {

        Matcher matcher = AMOUNT_PATTERN.matcher(body);

        if (!matcher.find()) {
            return null;
        }

        String value = matcher.group(1)
                .replace(",", "");

        return new BigDecimal(value);
    }

    private String extractSourceAccount(String body) {

        Matcher matcher = SOURCE_ACCOUNT_PATTERN.matcher(body);

        return matcher.find()
                ? matcher.group(1)
                : null;
    }

    private String extractDestinationAccount(String body) {

        Matcher matcher = DESTINATION_ACCOUNT_PATTERN.matcher(body);

        return matcher.find()
                ? matcher.group(1)
                : null;
    }

    private LocalDateTime extractOccurredAt(String body) {

        Matcher dateMatcher = DATE_PATTERN.matcher(body);
        Matcher timeMatcher = TIME_PATTERN.matcher(body);

        if (!dateMatcher.find() || !timeMatcher.find()) {
            return null;
        }

        String date = dateMatcher.group(1);
        String time = timeMatcher.group(1);

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm[:ss]");

        return LocalDateTime.parse(
                date + " " + time,
                formatter
        );
    }

    private boolean isMovement(String body) {

        String text = body.toLowerCase();

        return text.contains("transferiste")
                || text.contains("pagaste")
                || text.contains("recibiste")
                || text.contains("compraste")
                || text.contains("retiraste")
                || text.contains("consignaste");

    }
}