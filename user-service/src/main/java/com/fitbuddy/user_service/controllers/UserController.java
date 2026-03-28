package com.fitbuddy.user_service.controllers;

import com.fitbuddy.user_service.dtos.RegisterUserRequest;
import com.fitbuddy.user_service.dtos.UserDto;
import com.fitbuddy.user_service.responses.ApiResponse;
import com.fitbuddy.user_service.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> registerUser(@Valid @RequestBody RegisterUserRequest request) {
        UserDto userDto = userService.registerUser(request);
        ApiResponse<UserDto> response = ApiResponse.<UserDto>builder()
                .success(true)
                .data(userDto)
                .message("User registered successfully")
                .error(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getProfile(@PathVariable String id) {
        UserDto userDto = userService.getUserById(id);
        ApiResponse<UserDto> response = ApiResponse.<UserDto>builder()
                .success(true)
                .data(userDto)
                .error("User fetched successfully")
                .error(null)
                .build();
        return ResponseEntity.ok(response);
    }
}
