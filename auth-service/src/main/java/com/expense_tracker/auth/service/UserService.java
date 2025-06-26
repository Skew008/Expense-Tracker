package com.expense_tracker.auth.service;

import com.expense_tracker.auth.dto.UserResponseDto;
import com.expense_tracker.auth.entity.User;
import com.expense_tracker.auth.exception.UserNotFound;
import com.expense_tracker.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDto> getUsers() {
        return userRepository.findAll().stream().map(u->new UserResponseDto(u.getEmail(),u.getName())).toList();
    }

    public UserResponseDto getUser(String email) {

        User user = getUserDetails(email);

        return new UserResponseDto(user.getEmail(), user.getName());
    }

    public UserResponseDto updateUser(String email, String name) {

        User user = getUserDetails(email);

        if(name==null || name.isEmpty())
            throw new IllegalArgumentException("Name cannot be empty or null");

        user.setName(name);

        userRepository.save(user);

        return new UserResponseDto(user.getEmail(), user.getName());
    }

    public void removeUser(String email) {

        User user = getUserDetails(email);

        userRepository.deleteById(user.getId());
    }

    private User getUserDetails(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty())
            throw new UserNotFound("User not found");

        return user.get();
    }
}
