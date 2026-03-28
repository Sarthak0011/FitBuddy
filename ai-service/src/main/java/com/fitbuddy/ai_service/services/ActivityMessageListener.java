package com.fitbuddy.ai_service.services;

import com.fitbuddy.ai_service.dtos.ActivityEvent;

public interface ActivityMessageListener {
    void processActivity(ActivityEvent activity);
}
