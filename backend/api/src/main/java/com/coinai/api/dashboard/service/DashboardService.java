package com.coinai.api.dashboard.service;

import com.coinai.api.dashboard.dto.response.BarChartResponse;
import com.coinai.api.dashboard.dto.response.DashboardResponse;
import com.coinai.api.dashboard.dto.response.FamilyDashboardResponse;
import com.coinai.api.dashboard.dto.response.MonthlyTrendResponse;
import com.coinai.api.dashboard.dto.response.PieChartResponse;

import java.util.List;

public interface DashboardService {

    DashboardResponse getDashboard();

    FamilyDashboardResponse getFamilyDashboard();

    List<PieChartResponse> getPieChart();

    BarChartResponse getBarChart();

    List<MonthlyTrendResponse> getMonthlyTrend();

}