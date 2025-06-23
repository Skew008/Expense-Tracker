package com.expense_tracker.goal_service.repository;

import com.expense_tracker.goal_service.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GoalRepository extends JpaRepository<Goal, UUID> {

    List<Goal> findAllByEmail(String email);
}
