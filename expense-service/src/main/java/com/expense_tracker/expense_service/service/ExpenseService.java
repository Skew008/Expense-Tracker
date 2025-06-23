package com.expense_tracker.expense_service.service;

import com.expense_tracker.expense_service.dto.ExpenseRequest;
import com.expense_tracker.expense_service.dto.ExpenseResponse;
import com.expense_tracker.expense_service.entity.Expense;
import com.expense_tracker.expense_service.repository.ExpenseRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;


    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<ExpenseResponse> getExpenses(String email) {

        List<Expense> expenses = expenseRepository.findAllByEmail(email);

        return expenses.stream().map(ExpenseResponse::fromExpense).toList();
    }

    public ExpenseResponse addExpense(ExpenseRequest expenseRequest, String email) {
        if(validateExpense(expenseRequest)) {
            LocalDate date = DateUtil.parseDate(expenseRequest.date().toString());

            Expense expense = new Expense();
            expense.setEmail(email);
            expense.setAmount(expenseRequest.amount());
            expense.setCategory(expenseRequest.category().toUpperCase());
            expense.setDate(date);
            expense.setDescription(expenseRequest.description());

            Expense save = expenseRepository.save(expense);

            return new ExpenseResponse(save.getId(), email, expenseRequest.amount(), expenseRequest.category(), date, expenseRequest.description());
        }
        return null;
    }

    public ExpenseResponse getExpenseById(UUID id) throws BadRequestException {
        Optional<Expense> expense = expenseRepository.findById(id);
        if(expense.isEmpty())
            throw new BadRequestException("Invalid expense id");

        return new ExpenseResponse(id.toString(), expense.get().getEmail(), expense.get().getAmount(), expense.get().getCategory(), expense.get().getDate(), expense.get().getDescription());
    }

    public void updateExpense(UUID id, ExpenseRequest expenseRequest) throws BadRequestException {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if(optionalExpense.isEmpty())
            throw new BadRequestException("Invalid expense id");
        if(validateExpense(expenseRequest)) {
            LocalDate date = DateUtil.parseDate(expenseRequest.date().toString());

            Expense expense = optionalExpense.get();
            expense.setAmount(expenseRequest.amount());
            expense.setCategory(expenseRequest.category().toUpperCase());
            expense.setDate(date);
            expense.setDescription(expenseRequest.description());

            expenseRepository.save(expense);
        }
    }

    public void deleteExpense(UUID id) throws BadRequestException {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if(optionalExpense.isEmpty())
            throw new BadRequestException("Invalid expense id");
        expenseRepository.deleteById(id);
    }

    public List<ExpenseResponse> searchExpenses(String email, String category, LocalDate from, LocalDate to) {
        List<Expense> expenses = expenseRepository.findAllByEmail(email);
        Stream<Expense> expenseStream = expenses.stream()
                .filter(expense -> expense.getDate().isAfter(from) && expense.getDate().isBefore(to));
        if(category!=null)
            expenseStream = expenseStream.filter(expense -> expense.getCategory().equalsIgnoreCase(category));

        return expenseStream.map(ExpenseResponse::fromExpense).toList();
    }

    private boolean validateExpense(ExpenseRequest expense) {

        if(expense.amount()<0)
            throw new IllegalArgumentException("Amount cannot be negative");
        if(expense.category()==null || expense.date()==null)
            throw new IllegalArgumentException("Category or Date cannot be empty");
        return true;
    }
}
