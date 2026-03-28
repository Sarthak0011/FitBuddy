package com.fitbuddy.user_service.services;

import com.fitbuddy.user_service.dtos.RegisterUserRequest;
import com.fitbuddy.user_service.dtos.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDto registerUser(RegisterUserRequest request);

    UserDto getUserById(String id);

    Boolean existsById(String id);
}
