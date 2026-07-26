package com.coinai.api.email.service.impl;

import com.coinai.api.common.template.TemplateLoader;
import com.coinai.api.email.service.EmailService;
import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.core.exception.ResendException;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final Resend resend;
    private final TemplateLoader templateLoader;

    @Override
    public void sendPasswordRecoveryEmail(
            String to,
            String name,
            String recoveryLink
    ) throws ResendException {

        String html = templateLoader
                .load("password-recovery.html")
                .replace("{{name}}", name)
                .replace("{{link}}", recoveryLink);

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("CoinAI <onboarding@resend.dev>")
                .to(to)
                .subject("Recuperación de contraseña")
                .html(html)
                .build();

        resend.emails().send(params);

    }

}