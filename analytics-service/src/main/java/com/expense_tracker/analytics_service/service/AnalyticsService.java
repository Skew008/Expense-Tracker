package com.expense_tracker.analytics_service.service;

import com.expense_tracker.analytics_service.client.ExpenseClient;
import com.expense_tracker.analytics_service.dto.ExpenseDTO;
import com.expense_tracker.analytics_service.dto.ExpenseSummaryDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AnalyticsService {

    private final ExpenseClient expenseClient;

    public AnalyticsService(ExpenseClient expenseClient) {
        this.expenseClient = expenseClient;
    }

    public ExpenseSummaryDTO getMonthlySummary(String email, String startDate, String endDate) {

//        int currentYear = LocalDate.now().getYear();
//        Month currentMonth = LocalDate.now().getMonth();
//        LocalDate startDate = LocalDate.of(currentYear, currentMonth, 1);
//        LocalDate endDate = LocalDate.of(currentYear, currentMonth, currentMonth.length(startDate.isLeapYear()));
//        System.out.println(startDate.toString()+" "+endDate.toString());

        List<ExpenseDTO> expenses = expenseClient.getExpensesBetweenDates(email, startDate, endDate);

        Map<String, Double> categoryTotals = new HashMap<>();
        Double totalSpent = 0.0;

        for (ExpenseDTO expense : expenses) {
            totalSpent += expense.amount();

            categoryTotals.merge(
                    expense.category(),
                    expense.amount(),
                    Double::sum
            );
        }

        Map.Entry<String, Double> topCategory = categoryTotals.entrySet().stream().max(Map.Entry.comparingByValue()).orElseGet(()->null);

        return new ExpenseSummaryDTO(totalSpent, categoryTotals, Map.of("category", Objects.requireNonNull(topCategory).getKey(), "amount", topCategory.getValue()), Map.of("startDate", startDate, "endDate",endDate));
    }


}
