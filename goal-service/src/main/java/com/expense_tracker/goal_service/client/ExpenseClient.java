package com.expense_tracker.goal_service.client;

import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "expense-service")
public interface ExpenseClient {

    @GetMapping("/expense/total")
    public Double getTotalExpenses(@RequestHeader("X-Email") String email, @RequestParam String from, @RequestParam String to, @RequestParam(required = false) String category);
}
