package com.expense_tracker.goal_service.dto;

public record GoalProgressDTO(Double goalAmount, Double spent, Double remaining, Integer percentageUsed) {
}
