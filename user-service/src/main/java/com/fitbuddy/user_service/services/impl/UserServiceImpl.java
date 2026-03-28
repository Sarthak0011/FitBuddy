package com.fitbuddy.user_service.services.impl;

import com.fitbuddy.user_service.dtos.RegisterUserRequest;
import com.fitbuddy.user_service.dtos.UserDto;
import com.fitbuddy.user_service.entities.User;
import com.fitbuddy.user_service.repositories.UserRepository;
import com.fitbuddy.user_service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto registerUser(RegisterUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User with this email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    @Override
    public UserDto getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id: " + id + "does not exists"));
        return convertToDto(user);
    }

    @Override
    public Boolean existsById(String id) {
        return userRepository.existsById(id);
    }


    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .lastName(user.getLastName())
                .firstName(user.getLastName())
                .build();
    }
}
