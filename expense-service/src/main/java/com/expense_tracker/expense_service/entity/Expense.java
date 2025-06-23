package com.expense_tracker.expense_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Expense {

    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Double amount;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private LocalDate date;
    @Column
    private String description;

    public Expense() {
    }

    public String getId() {
        return id.toString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
