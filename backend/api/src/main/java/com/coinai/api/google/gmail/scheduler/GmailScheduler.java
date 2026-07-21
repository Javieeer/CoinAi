/* package com.coinai.api.google.gmail.scheduler;

import com.coinai.api.google.gmail.service.GmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GmailScheduler {

    private final GmailService gmailService;

    @Scheduled(fixedDelay = 60000)
    public void sync() {

        try {

            gmailService.listMessages();

        } catch (Exception e) {

            log.error("Error sincronizando Gmail", e);

        }

    }

} */