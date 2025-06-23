package com.expense_tracker.goal_service.dto;

import com.expense_tracker.goal_service.entity.Goal;

import java.time.LocalDate;

public record GoalResponse(String  id, String email, String title, Double amount, String category, LocalDate startDate, LocalDate endDate, String description) {

    public static GoalResponse fromGoal(Goal goal) {
        return new GoalResponse(goal.getId(), goal.getEmail(), goal.getTitle(), goal.getAmount(), goal.getCategory(), goal.getStartDate(), goal.getEndDate(), goal.getDescription());
    }
}
