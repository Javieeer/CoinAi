package com.coinai.api.google.gmail.service;

import com.coinai.api.google.entity.GoogleCredential;
import com.coinai.api.google.repository.GoogleCredentialRepository;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
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

    @Override
    public List<String> listMessages() throws Exception {

        User user = authenticatedUserService.getCurrentUser();

        GoogleCredential credential =
                credentialRepository.findByUserId(user.getId())
                        .orElseThrow();

        Gmail gmail =
                gmailClientFactory.create(credential);

        ListMessagesResponse response =
                gmail.users()
                        .messages()
                        .list("me")
                        .setMaxResults(10L)
                        .execute();

        List<String> ids = new ArrayList<>();

        if (response.getMessages() != null) {

            for (Message message : response.getMessages()) {
                ids.add(message.getId());
            }

        }

        return ids;
    }

}