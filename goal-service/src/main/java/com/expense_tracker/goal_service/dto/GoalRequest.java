package com.expense_tracker.goal_service.dto;

import java.time.LocalDate;

public record GoalRequest(String title, Double amount, String category, LocalDate startDate, LocalDate endDate, String description) {
}
