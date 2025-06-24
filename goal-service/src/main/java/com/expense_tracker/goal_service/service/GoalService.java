package com.expense_tracker.goal_service.service;

import com.expense_tracker.goal_service.client.ExpenseClient;
import com.expense_tracker.goal_service.dto.GoalProgressDTO;
import com.expense_tracker.goal_service.dto.GoalRequest;
import com.expense_tracker.goal_service.dto.GoalResponse;
import com.expense_tracker.goal_service.entity.Goal;
import com.expense_tracker.goal_service.repository.GoalRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GoalService {

    private final GoalRepository goalRepository;
    private final ExpenseClient expenseClient;

    public GoalService(GoalRepository goalRepository, ExpenseClient expenseClient) {
        this.goalRepository = goalRepository;
        this.expenseClient = expenseClient;
    }

    public List<GoalResponse> getGoals(String email) {
        return goalRepository.findAllByEmail(email).stream().map(GoalResponse::fromGoal).toList();
    }

    public GoalResponse creteGoal(String email, GoalRequest goalRequest) {
        if(validateGoal(goalRequest)) {
            LocalDate startDate = DateUtil.parseDate(goalRequest.startDate().toString());
            LocalDate endDate = DateUtil.parseDate(goalRequest.endDate().toString());

            Goal goal = new Goal();
            goal.setEmail(email);
            goal.setTitle(goalRequest.title());
            goal.setAmount(goalRequest.amount());
            goal.setCategory(goalRequest.category().toUpperCase());
            goal.setStartDate(startDate);
            goal.setEndDate(endDate);
            goal.setDescription(goalRequest.description());

            Goal save = goalRepository.save(goal);
            return new GoalResponse(save.getId(), email, save.getTitle(), save.getAmount(), goal.getCategory(), startDate, endDate, save.getDescription());
        }
        return null;
    }

    public GoalResponse getGoalById(UUID id) throws BadRequestException {
        Optional<Goal> goal = goalRepository.findById(id);
        if(goal.isEmpty())
            throw new BadRequestException("Invalid goal id");

        return goal.map(GoalResponse::fromGoal).get();
    }

    public void deleteGoal(UUID id) throws BadRequestException {
        if(!goalRepository.existsById(id))
            throw new BadRequestException("Invalid goal id");
        goalRepository.deleteById(id);
    }

    public void updateGoal(UUID id, GoalRequest goalRequest) throws BadRequestException {
        if(!goalRepository.existsById(id))
            throw new BadRequestException("Invalid goal id");
        if(validateGoal(goalRequest)) {
            LocalDate startDate = DateUtil.parseDate(goalRequest.startDate().toString());
            LocalDate endDate = DateUtil.parseDate(goalRequest.endDate().toString());

            Goal goal = new Goal();
            goal.setTitle(goalRequest.title());
            goal.setAmount(goalRequest.amount());
            goal.setCategory(goalRequest.category().toUpperCase());
            goal.setStartDate(startDate);
            goal.setEndDate(endDate);
            goal.setDescription(goalRequest.description());

            goalRepository.save(goal);
        }
    }

    public GoalProgressDTO getProgress(UUID id, String email) throws BadRequestException {
        Optional<Goal> goal = goalRepository.findById(id);
        if(goal.isEmpty())
            throw new BadRequestException("Invalid goal id");

        Double spent = expenseClient.getTotalExpenses(email, goal.get().getStartDate().toString(), goal.get().getEndDate().toString(), goal.get().getCategory());
        Double remining = goal.get().getAmount() - spent;
        Integer percentage = (int) (spent*100/goal.get().getAmount());

        return new GoalProgressDTO(goal.get().getAmount(), spent, remining, percentage);
    }

    private boolean validateGoal(GoalRequest goal) {

        if(goal.title()==null || goal.title().isEmpty())
            throw new IllegalArgumentException("Title cannot be empty");
        if(goal.amount()==null || goal.amount()<0)
            throw new IllegalArgumentException("Amount cannot be negative");
        if(goal.category()==null || goal.category().isEmpty())
            throw new IllegalArgumentException("Category or Date cannot be empty");
        if(goal.startDate()==null || goal.endDate()==null)
            throw new IllegalArgumentException("Start and End dates cannot be empty");
        return true;
    }
}
