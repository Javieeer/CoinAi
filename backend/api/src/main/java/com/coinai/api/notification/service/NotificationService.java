package com.coinai.api.notification.service;

import com.coinai.api.notification.BudgetAlertLevel;
import com.coinai.api.user.entity.User;

public interface NotificationService {

    void notifyBudgetAlert(
            User user,
            String category,
            BudgetAlertLevel level,
            int percentage
    );

}