package com.coinai.api.dashboard.controller;

import com.coinai.api.dashboard.dto.response.DashboardResponse;
import com.coinai.api.dashboard.dto.response.FamilyDashboardResponse;
import com.coinai.api.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public DashboardResponse getDashboard() {
        return dashboardService.getDashboard();
    }

    @GetMapping("/family")
    public FamilyDashboardResponse getFamilyDashboard() {
        return dashboardService.getFamilyDashboard();
    }
}