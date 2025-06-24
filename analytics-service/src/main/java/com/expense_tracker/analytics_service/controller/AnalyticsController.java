package com.expense_tracker.analytics_service.controller;

import com.expense_tracker.analytics_service.dto.ExpenseSummaryDTO;
import com.expense_tracker.analytics_service.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("overview")
    public ResponseEntity<ExpenseSummaryDTO> getOverview(@RequestHeader("X-Email") String email, @RequestParam String from, @RequestParam String to) {
        return ResponseEntity.ok(analyticsService.getMonthlySummary(email, from, to));
    }
}
