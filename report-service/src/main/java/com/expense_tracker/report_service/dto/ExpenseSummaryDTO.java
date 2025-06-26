package com.expense_tracker.report_service.dto;

import java.util.Map;

public record ExpenseSummaryDTO(Double totalSpent, Map<String, Double> categoryBreakdown, Map<String, Object> topCategory, Map<String, String> period) {
}
