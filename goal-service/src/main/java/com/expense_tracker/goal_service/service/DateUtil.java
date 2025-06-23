package com.expense_tracker.goal_service.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    private static final
    DateTimeFormatter PREFERRED_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate parseDate(String inputDate) {
        try {
            return LocalDate.parse(inputDate, PREFERRED_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected format is yyyy-MM-dd");
        }
    }
}
