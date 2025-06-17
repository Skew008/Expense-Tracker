package com.expense_tracker.auth.security;

import java.util.regex.Pattern;

public class ValidationUtil {

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_PASSWORD_LENGTH = 16;

    private ValidationUtil() {
    }

    public static String validateRegistration(String name, String email, String password) {
        if (email == null || email.isEmpty()) {
            return "Email is missing or empty.";
        }
        if (!isValidEmail(email)) {
            return "Invalid email format.";
        }
        if (name == null || name.isEmpty()) {
            return "First name is missing or empty.";
        }
        if (name.length() > MAX_NAME_LENGTH) {
            return "First name exceeds the maximum length of " + MAX_NAME_LENGTH + " characters.";
        }
        if (password == null || password.isEmpty()) {
            return "Password is missing or empty.";
        }
        if (!isValidPassword(password)) {
            return "Password does not meet the required criteria.";
        }
        return null;
    }

    public static String validateLogin(String email, String password) {
        if (email == null || email.isEmpty()) {
            return "Email is missing or empty.";
        }
        if (!isValidEmail(email)) {
            return "Invalid email format.";
        }
        if (password == null || password.isEmpty()) {
            return "Password is missing or empty.";
        }
        return null;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailRegex, email) && !email.contains("..");
    }

    public static boolean isValidPassword(String password) {
        if (password.length() > MAX_PASSWORD_LENGTH) {
            return false;
        }
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }
        if (!password.matches(".*[a-z].*")) {
            return false;
        }
        if (!password.matches(".*\\d.*")) {
            return false;
        }
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            return false;
        }
        return true;
    }
}
