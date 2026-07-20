package com.coinai.api.automation.learning.entity;

import com.coinai.api.category.entity.Category;
import com.coinai.api.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "merchant_rules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false)
    private String rawMerchant;

    @Column(nullable = false)
    private String normalizedMerchant;

    @ManyToOne
    private Category category;

    @Column(nullable = false)
    @Builder.Default
    private Integer timesUsed = 1;

    private LocalDateTime lastUsed;

    @Column(nullable = false)
    @Builder.Default
    private Boolean confirmedByUser = false;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

}