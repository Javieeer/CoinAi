package com.coinai.api.google.gmail.service;

import com.coinai.api.automation.parser.BankDetector;
import com.coinai.api.automation.parser.BankType;
import com.coinai.api.automation.parser.bank.EmailParser;
import com.coinai.api.automation.parser.bank.EmailParserFactory;
import com.coinai.api.automation.movement.dto.MovementDraft;
import com.coinai.api.automation.movement.importer.MovementImportService;
import com.coinai.api.automation.email.service.ProcessedEmailService;
import com.coinai.api.google.entity.GoogleCredential;
/* import com.coinai.api.google.gmail.cleaner.EmailBodyCleaner; */
import com.coinai.api.google.gmail.cleaner.EmailBodyCleanerFactory;
import com.coinai.api.google.gmail.dto.GmailMessageResponse;
import com.coinai.api.google.gmail.provider.EmailProviderService;
import com.coinai.api.google.gmail.util.GmailBodyExtractor;
import com.coinai.api.google.repository.GoogleCredentialRepository;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePartHeader;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GmailServiceImpl implements GmailService {

    private final GoogleCredentialRepository credentialRepository;
    private final GmailClientFactory gmailClientFactory;
    private final AuthenticatedUserService authenticatedUserService;
    private final BankDetector bankDetector;
    private final EmailProviderService emailProviderService;
    private final GmailBodyExtractor gmailBodyExtractor;
    /* private final EmailBodyCleaner emailBodyCleaner; */
    private final EmailParserFactory emailParserFactory;
    private final EmailBodyCleanerFactory emailBodyCleanerFactory;
    private final ProcessedEmailService processedEmailService;
    private final MovementImportService movementImportService;

    @Override
    public List<GmailMessageResponse> listMessages() throws Exception {

        User user = authenticatedUserService.getCurrentUser();

        return processUser(user);
        
    }

    private List<GmailMessageResponse> processUser( User user) throws Exception {

        GoogleCredential credential =
                credentialRepository.findByUserId(user.getId())
                        .orElseThrow();

        Gmail gmail =
                gmailClientFactory.create(credential);

        String query = emailProviderService.buildGmailQuery();

        ListMessagesResponse response =
            gmail.users()
                    .messages()
                    .list("me")
                    .setQ(query)
                    .setMaxResults(20L)
                    .execute();

        List<GmailMessageResponse> messages = new ArrayList<>();

        if (response.getMessages() != null) {

            for (Message message : response.getMessages()) {

                if (processedEmailService.exists(message.getId())) {
                        continue;
                }

                Message fullMessage = gmail.users()
                        .messages()
                        .get("me", message.getId())
                        .execute();

                String from = "";
                String subject = "";
                String date = "";

                BankType bankType =
                    bankDetector.detect(
                            from,
                            subject,
                            fullMessage.getSnippet()
                    );

                String body =
                        gmailBodyExtractor.extract(
                                fullMessage.getPayload()
                        );

                body = emailBodyCleanerFactory
                        .get(bankType)
                        .clean(body);

                for (MessagePartHeader header : fullMessage.getPayload().getHeaders()) {

                    switch (header.getName()) {

                        case "From" -> from = header.getValue();

                        case "Subject" -> subject = header.getValue();

                        case "Date" -> date = header.getValue();

                    }

                }

                EmailParser parser =
                        emailParserFactory.get(bankType);

                MovementDraft draft =
                        parser.parse(body);

                if (draft == null) {
                        continue;
                }

                movementImportService.importMovement(draft);

                processedEmailService.save(
                        message.getId(),
                        draft.getBankType()
                );

                messages.add(
                        GmailMessageResponse.builder()
                                .id(fullMessage.getId())
                                .from(from)
                                .subject(subject)
                                .date(date)
                                .snippet(fullMessage.getSnippet())
                                .body(body)
                                .bankType(bankType)
                                .build()
                );

            }

        }

        return messages;
    }

}