package com.fitbuddy.ai_service.services.impl;

import com.fitbuddy.ai_service.dtos.ActivityEvent;
import com.fitbuddy.ai_service.models.Recommendation;
import com.fitbuddy.ai_service.repositories.RecommendationRepository;
import com.fitbuddy.ai_service.services.ActivityAiService;
import com.fitbuddy.ai_service.services.ActivityMessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityMessageListenerImpl implements ActivityMessageListener {

    private final ActivityAiService activityAiService;
    private final RecommendationRepository recommendationRepository;

    @Override
    @KafkaListener(topics = "${kafka.topic.name}", groupId = "activity-processor-group")
    public void processActivity(ActivityEvent activity) {
        log.info("Received an event for userId {}", activity.getUserId());
        Recommendation recommendation = activityAiService.generateRecommendation(activity);
        if (recommendation != null) {
            recommendationRepository.save(recommendation);
            log.info("Saved recommendation in DB");
        } else {
            log.warn("Generated recommendation was null for userId {}", activity.getUserId());
        }
    }
}
