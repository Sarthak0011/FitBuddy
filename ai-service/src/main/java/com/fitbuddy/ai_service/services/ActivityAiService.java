package com.fitbuddy.ai_service.services;

import com.fitbuddy.ai_service.dtos.ActivityEvent;
import com.fitbuddy.ai_service.models.Recommendation;

public interface ActivityAiService {
    Recommendation generateRecommendation(ActivityEvent activity);
}
