package com.coinai.api.automation.email.entity;

import com.coinai.api.automation.parser.BankType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "processed_emails")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessedEmail {

    @Id
    private UUID id;

    @Column(name = "gmail_message_id", nullable = false, unique = true)
    private String gmailMessageId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BankType bankType;

    @Column(nullable = false)
    private LocalDateTime processedAt;

}