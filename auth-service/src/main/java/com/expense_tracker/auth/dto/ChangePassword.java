package com.expense_tracker.auth.dto;

public record ChangePassword(String oldPassword, String newPassword) {
}
