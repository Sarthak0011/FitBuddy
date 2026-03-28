package com.fitbuddy.ai_service.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class RecommendationDto {
    private String id;
    private String activityId;
    private String userId;
    private String recommendation;
    private List<String> improvements;
    private List<String> suggestions;
    private List<String> safety;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
