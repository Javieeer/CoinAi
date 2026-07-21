package com.coinai.api.google.gmail.provider;

import java.util.List;

public interface EmailProviderService {

    List<String> getAllowedDomains();

    String buildGmailQuery();

}