package com.expense_tracker.auth.controller;

import com.expense_tracker.auth.dto.AuthResponse;
import com.expense_tracker.auth.dto.ChangePassword;
import com.expense_tracker.auth.dto.LoginRequest;
import com.expense_tracker.auth.dto.RegisterRequest;
import com.expense_tracker.auth.service.AuthService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) throws BadRequestException {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest registerRequest) throws BadRequestException {
        authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("change-password")
    public ResponseEntity<Object> passwordChange(Authentication authentication, @RequestBody ChangePassword changePassword) throws BadRequestException {
        String email = authentication.getName();
        authService.changePassword(email, changePassword);
        return ResponseEntity.ok("Password Changed Successfully");
    }
}
