package com.coinai.api.google.gmail.controller;

import com.coinai.api.google.gmail.service.GmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gmail")
@RequiredArgsConstructor
public class GmailController {

    private final GmailService gmailService;

    @GetMapping("/messages")
    public List<String> messages() throws Exception {

        return gmailService.listMessages();

    }

}