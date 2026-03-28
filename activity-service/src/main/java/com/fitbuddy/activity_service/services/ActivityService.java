package com.fitbuddy.activity_service.services;

import com.fitbuddy.activity_service.dtos.ActivityDto;
import com.fitbuddy.activity_service.dtos.ActivityRequest;

public interface ActivityService {
    ActivityDto trackActivity(ActivityRequest request);
}
