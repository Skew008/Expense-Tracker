package com.expense_tracker.goal_service.controller;

import com.expense_tracker.goal_service.dto.GoalProgressDTO;
import com.expense_tracker.goal_service.dto.GoalRequest;
import com.expense_tracker.goal_service.dto.GoalResponse;
import com.expense_tracker.goal_service.service.GoalService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("goal")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping
    public ResponseEntity<GoalResponse> createGoal(@RequestHeader("X-Email") String email, @RequestBody GoalRequest goalRequest) {
        return ResponseEntity.status(201).body(goalService.creteGoal(email, goalRequest));
    }

    @GetMapping
    public ResponseEntity<List<GoalResponse>> getGoals(@RequestHeader("X-Email") String email) {
        return ResponseEntity.ok(goalService.getGoals(email));
    }

    @GetMapping("{id}")
    public ResponseEntity<GoalResponse> getGoalById(@PathVariable UUID id) throws BadRequestException {
        return ResponseEntity.ok(goalService.getGoalById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateGoal(@PathVariable UUID id, @RequestBody GoalRequest goalRequest) throws BadRequestException {
        goalService.updateGoal(id, goalRequest);
        return ResponseEntity.ok("Goal updated successfully");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteGoal(@PathVariable UUID id) throws BadRequestException {
        goalService.deleteGoal(id);
        return ResponseEntity.ok("Deleted goal successfully");
    }

    @GetMapping("{id}/progress")
    public ResponseEntity<GoalProgressDTO> getProgress (@PathVariable UUID id, @RequestHeader("X-Email") String email) throws BadRequestException {
        return ResponseEntity.ok(goalService.getProgress(id, email));
    }
}
