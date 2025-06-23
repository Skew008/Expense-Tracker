package com.expense_tracker.expense_service.dto;

import com.expense_tracker.expense_service.entity.Expense;

import java.time.LocalDate;

public record ExpenseResponse(String id, String email, Double amount, String category, LocalDate date, String description) {

    public static ExpenseResponse fromExpense(Expense expense) {
        return new ExpenseResponse(expense.getId(), expense.getEmail(), expense.getAmount(), expense.getCategory(), expense.getDate(), expense.getDescription());
    }
}
