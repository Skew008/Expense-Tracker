package com.expense_tracker.report_service.client;

import com.expense_tracker.report_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "auth-service")
public interface UsersClient {

    @GetMapping("/users/all")
    List<UserDTO> getUsers();
}
