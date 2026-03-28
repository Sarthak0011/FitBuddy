package com.fitbuddy.user_service.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
}
