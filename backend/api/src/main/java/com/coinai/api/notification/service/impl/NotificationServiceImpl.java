package com.coinai.api.notification.service.impl;

import com.coinai.api.notification.BudgetAlertLevel;
import com.coinai.api.notification.service.NotificationService;
import com.coinai.api.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationServiceImpl
        implements NotificationService {

    @Override
    public void notifyBudgetAlert(
            User user,
            String category,
            BudgetAlertLevel level,
            int percentage
    ) {

        log.info("""
                ========= BUDGET ALERT =========
                User: {}
                Category: {}
                Level: {}
                Percentage: {}%
                ===============================
                """,
                user.getEmail(),
                category,
                level,
                percentage
        );

    }

}