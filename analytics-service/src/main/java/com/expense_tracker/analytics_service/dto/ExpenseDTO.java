package com.expense_tracker.analytics_service.dto;

import java.time.LocalDate;

public record ExpenseDTO(String id, String email, Double amount, String category, LocalDate date, String description) {
}
