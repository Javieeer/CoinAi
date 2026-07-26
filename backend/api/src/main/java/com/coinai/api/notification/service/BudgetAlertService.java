package com.coinai.api.notification.service;

import com.coinai.api.movement.entity.Movement;

public interface BudgetAlertService {

    void checkBudgetAlerts(Movement movement);

}