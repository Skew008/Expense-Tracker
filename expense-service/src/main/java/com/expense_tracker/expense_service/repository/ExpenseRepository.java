package com.expense_tracker.expense_service.repository;

import com.expense_tracker.expense_service.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    Optional<Expense> findByEmail(String email);
    List<Expense> findAllByEmail(String email);

    @Query(value = """
    SELECT COALESCE(SUM(amount), 0)
    FROM expense
    WHERE email = :email AND date BETWEEN :from AND :to
    AND (:category IS NULL OR category = :category)
""", nativeQuery = true)
    Double getTotalByDateRangeAndCategory(
            @Param("email") String email,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            @Param("category") String category
    );
}
