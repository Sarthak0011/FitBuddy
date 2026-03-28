package com.fitbuddy.ai_service.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private Boolean success;
    private T data;
    private String message;
    private String error;
}
