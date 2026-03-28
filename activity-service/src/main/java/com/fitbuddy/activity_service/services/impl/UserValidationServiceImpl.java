package com.fitbuddy.activity_service.services.impl;

import com.fitbuddy.activity_service.dtos.UserServiceDto;
import com.fitbuddy.activity_service.services.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserValidationServiceImpl implements UserValidationService {

    private final WebClient userServiceWebClient;

    public Boolean validateUserById(String userId) {
        UserServiceDto<Boolean> response =  userServiceWebClient.get()
                .uri("/api/users/{userId}/validate", userId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<UserServiceDto<Boolean>>() {})
                .block();
        return response != null ? response.getData() : false;
    }
}
