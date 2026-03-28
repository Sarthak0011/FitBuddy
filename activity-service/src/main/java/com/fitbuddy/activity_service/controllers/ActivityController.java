package com.fitbuddy.activity_service.controllers;

import com.fitbuddy.activity_service.dtos.ActivityDto;
import com.fitbuddy.activity_service.dtos.ActivityRequest;
import com.fitbuddy.activity_service.responses.ApiResponse;
import com.fitbuddy.activity_service.services.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ApiResponse<ActivityDto>> trackActivity(@RequestBody ActivityRequest request) {
        ActivityDto activityDto = activityService.trackActivity(request);
        ApiResponse<ActivityDto> response = ApiResponse.<ActivityDto>builder()
                .success(true)
                .data(activityDto)
                .message("Activity tracked successfully")
                .error(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
