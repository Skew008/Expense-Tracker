package com.expense_tracker.auth.service;

import com.expense_tracker.auth.dto.AuthResponse;
import com.expense_tracker.auth.dto.ChangePassword;
import com.expense_tracker.auth.dto.LoginRequest;
import com.expense_tracker.auth.dto.RegisterRequest;
import com.expense_tracker.auth.entity.User;
import com.expense_tracker.auth.repository.UserRepository;
import com.expense_tracker.auth.security.JwtUtil;
import com.expense_tracker.auth.security.ValidationUtil;
import jakarta.persistence.EntityExistsException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public void register(RegisterRequest registerRequest) {

        String validateRegistration = ValidationUtil.validateRegistration(registerRequest.name(), registerRequest.email(), registerRequest.password());
        if(validateRegistration!=null)
            throw new BadRequestException(validateRegistration);

        if(userRepository.existsByEmail(registerRequest.email()))
            throw new EntityExistsException();

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(registerRequest.name());
        user.setEmail(registerRequest.email());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest loginRequest) {

        String validateLogin = ValidationUtil.validateLogin(loginRequest.email(), loginRequest.password());
        if(validateLogin!=null)
            throw new BadRequestException(validateLogin);

        if(!userRepository.existsByEmail(loginRequest.email()))
            throw new NotFoundException("User not present");

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Unauthorized request. Please verify credentials and try again.", e);
        }


        return new AuthResponse(jwtUtil.generateToken(loginRequest.email()));
    }

    public void changePassword(String email, ChangePassword changePassword) {

        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty())
            throw new NotFoundException("User does not exist");

        User user1 = user.get();
        if(!passwordEncoder.matches(changePassword.oldPassword(), user1.getPassword()))
            throw new BadRequestException("Incorrect Password");

        if(!ValidationUtil.isValidPassword(changePassword.newPassword()))
            throw new BadRequestException("Password does not meet the required criteria");

        user1.setPassword(passwordEncoder.encode(changePassword.newPassword()));
        userRepository.save(user1);
    }
}
