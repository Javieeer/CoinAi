package com.coinai.api.dashboard.service;

import com.coinai.api.dashboard.dto.response.DashboardResponse;
import com.coinai.api.dashboard.dto.response.FamilyDashboardResponse;

public interface DashboardService {

    DashboardResponse getDashboard();

    FamilyDashboardResponse getFamilyDashboard();

}