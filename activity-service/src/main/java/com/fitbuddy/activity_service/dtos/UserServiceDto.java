package com.fitbuddy.activity_service.dtos;

import lombok.Data;

@Data
public class UserServiceDto<T> {
    private Boolean success;
    private T data;
    private String message;
    private String error;
}
