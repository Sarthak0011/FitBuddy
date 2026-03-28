package com.fitbuddy.ai_service.services.impl;

import com.fitbuddy.ai_service.dtos.ActivityEvent;
import com.fitbuddy.ai_service.services.ActivityMessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityMessageListenerImpl implements ActivityMessageListener {

    @Override
    @KafkaListener(topics = "${kafka.topic.name}", groupId = "activity-processor-group")
    public void processActivity(ActivityEvent activity) {
        log.info("Received an event for userId {}", activity.getUserId());
    }
}
