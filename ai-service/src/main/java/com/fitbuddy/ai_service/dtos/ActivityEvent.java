package com.fitbuddy.ai_service.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ActivityEvent {
    private String activityId;
    private String userId;
    private String type;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private Map<String, Object> additionalMatrics;
    private LocalDateTime createdAt;
}
