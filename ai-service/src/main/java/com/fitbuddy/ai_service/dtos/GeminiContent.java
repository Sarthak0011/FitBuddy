package com.fitbuddy.ai_service.dtos;

import lombok.Data;

import java.util.List;

@Data
public class GeminiContent {
    private List<GeminiPart> parts;
}
