package com.expense_tracker.expense_service.dto;

import java.time.LocalDate;

public record ExpenseRequest(Double amount, String category, LocalDate date, String description) {
}
