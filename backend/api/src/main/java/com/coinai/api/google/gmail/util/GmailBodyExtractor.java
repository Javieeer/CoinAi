package com.coinai.api.google.gmail.util;

import com.google.api.services.gmail.model.MessagePart;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class GmailBodyExtractor {

    public String extract(MessagePart part) {

        if (part == null) {
            return "";
        }

        if (part.getBody() != null
                && part.getBody().getData() != null) {

            return decode(part.getBody().getData());

        }

        if (part.getParts() != null) {

            for (MessagePart child : part.getParts()) {

                String body = extract(child);

                if (!body.isBlank()) {
                    return body;
                }

            }

        }

        return "";

    }

    private String decode(String value) {

        byte[] decoded = Base64.getUrlDecoder().decode(value);

        return new String(decoded, StandardCharsets.UTF_8);

    }

}