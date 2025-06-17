package com.expense_tracker.auth.controller;

import com.expense_tracker.auth.dto.UserResponseDto;
import com.expense_tracker.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("me")
    public ResponseEntity<UserResponseDto> getUserDetails(Authentication authentication) {
        return ResponseEntity.ok(userService.getUser(authentication.getName()));
    }

    @PutMapping("me")
    public ResponseEntity<UserResponseDto> updateUserDetails(Authentication authentication, @RequestParam String name) {
        return ResponseEntity.ok(userService.updateUser(authentication.getName(), name));
    }

    @DeleteMapping("me")
    public ResponseEntity<String> removeUserDetails(Authentication authentication) {
        userService.removeUser(authentication.getName());
        return ResponseEntity.ok("User deleted Successfully");
    }
}
