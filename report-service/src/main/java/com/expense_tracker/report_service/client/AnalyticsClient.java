package com.expense_tracker.report_service.client;

import com.expense_tracker.report_service.dto.ExpenseSummaryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "analytics-service")
public interface AnalyticsClient {

    @GetMapping("/analytics/overview")
    ExpenseSummaryDTO getOverview(@RequestHeader("X-Email") String email, @RequestParam String from, @RequestParam String to);
}
