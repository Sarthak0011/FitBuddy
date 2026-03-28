package com.fitbuddy.ai_service.services;

import com.fitbuddy.ai_service.dtos.RecommendationDto;

import java.util.List;

public interface RecommendationService {
    List<RecommendationDto> findByUserId(String userId);
    RecommendationDto findByActivityId(String activityId);
}
