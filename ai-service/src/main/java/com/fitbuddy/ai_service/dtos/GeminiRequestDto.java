package com.fitbuddy.ai_service.dtos;

import lombok.Data;

import java.util.List;

@Data
public class GeminiRequestDto {
    private List<GeminiContent> contents;
}
