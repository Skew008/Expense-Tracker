package com.expense_tracker.expense_service.controller;

import com.expense_tracker.expense_service.dto.ExpenseRequest;
import com.expense_tracker.expense_service.dto.ExpenseResponse;
import com.expense_tracker.expense_service.service.DateUtil;
import com.expense_tracker.expense_service.service.ExpenseService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getExpenses(@RequestHeader("X-Email") String email) {
        return ResponseEntity.ok(expenseService.getExpenses(email));
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> addExpense(@RequestHeader("X-Email") String email, @RequestBody ExpenseRequest expense) {
        return ResponseEntity.status(201).body(expenseService.addExpense(expense,email));
    }

    @GetMapping("{id}")
    public ResponseEntity<ExpenseResponse> getExpense(@PathVariable UUID id) throws BadRequestException {
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateExpense(@PathVariable UUID id, @RequestBody ExpenseRequest expense) throws BadRequestException {
        expenseService.updateExpense(id, expense);
        return ResponseEntity.ok("Expense updated successfully");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteExpense(@PathVariable UUID id) throws BadRequestException {
        expenseService.deleteExpense(id);
        return ResponseEntity.ok("Expense deleted successfully");
    }

    @GetMapping("search")
    public ResponseEntity<List<ExpenseResponse>> searchExpenses(@RequestHeader("X-Email") String email, @RequestParam(required = false) String category, @RequestParam String from, @RequestParam String to) {
        LocalDate fromDate = DateUtil.parseDate(from);
        LocalDate toDate = DateUtil.parseDate(to);
        return ResponseEntity.ok(expenseService.searchExpenses(email, category, fromDate, toDate));
    }
}
