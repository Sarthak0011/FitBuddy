package com.fitbuddy.activity_service.dtos;

import com.fitbuddy.activity_service.enums.ActivityType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ActivityDto {
    private String id;
    private String userId;
    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private Map<String, Object> additionalMatrics;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
