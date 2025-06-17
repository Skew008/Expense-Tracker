package com.expense_tracker.auth.dto;

public record RegisterRequest(String email, String password, String name) {
}
