package com.fitbuddy.activity_service.services.impl;

import com.fitbuddy.activity_service.dtos.ActivityDto;
import com.fitbuddy.activity_service.dtos.ActivityRequest;
import com.fitbuddy.activity_service.models.Activity;
import com.fitbuddy.activity_service.repositories.ActivityRepository;
import com.fitbuddy.activity_service.services.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    @Override
    public ActivityDto trackActivity(ActivityRequest request) {
        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMatrics(request.getAdditionalMatrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        return convertToDto(savedActivity);
    }

    private ActivityDto convertToDto(Activity activity) {
        return ActivityDto.builder()
                .id(activity.getId())
                .userId(activity.getUserId())
                .type(activity.getType())
                .duration(activity.getDuration())
                .caloriesBurned(activity.getCaloriesBurned())
                .startTime(activity.getStartTime())
                .additionalMatrics(activity.getAdditionalMatrics())
                .createdAt(activity.getCreatedAt())
                .updatedAt(activity.getUpdatedAt())
                .build();
    }
}
