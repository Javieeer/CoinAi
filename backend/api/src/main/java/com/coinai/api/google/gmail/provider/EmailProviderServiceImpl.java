package com.coinai.api.google.gmail.provider;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailProviderServiceImpl implements EmailProviderService {

    @Override
    public List<String> getAllowedDomains() {

        return Arrays.stream(EmailProvider.values())
                .map(EmailProvider::getDomain)
                .toList();

    }

    @Override
    public String buildGmailQuery() {

        String domains = Arrays.stream(EmailProvider.values())
                .map(provider -> "from:" + provider.getDomain())
                .collect(Collectors.joining(" OR "));

        return "(" + domains + ") is:unread newer_than:30d";

    }

}