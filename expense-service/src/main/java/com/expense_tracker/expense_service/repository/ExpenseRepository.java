package com.expense_tracker.expense_service.repository;

import com.expense_tracker.expense_service.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    Optional<Expense> findByEmail(String email);
    List<Expense> findAllByEmail(String email);
}
