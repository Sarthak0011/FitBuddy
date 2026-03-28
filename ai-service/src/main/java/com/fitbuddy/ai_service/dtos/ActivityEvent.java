package com.fitbuddy.ai_service.dtos;

import com.fitbuddy.ai_service.enums.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ActivityEvent {
    private String activityId;
    private String userId;
    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private Map<String, Object> additionalMatrics;
    private LocalDateTime createdAt;
}
