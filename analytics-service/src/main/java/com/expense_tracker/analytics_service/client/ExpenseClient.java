package com.expense_tracker.analytics_service.client;

import com.expense_tracker.analytics_service.dto.ExpenseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "expense-service")
public interface ExpenseClient {

    @GetMapping("/expense/search")
    List<ExpenseDTO> getExpensesBetweenDates(
            @RequestHeader("X-Email") String email,
            @RequestParam("from") String from,
            @RequestParam("to") String to
    );
}
