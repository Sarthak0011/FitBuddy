package com.fitbuddy.ai_service.controllers;

import com.fitbuddy.ai_service.dtos.RecommendationDto;
import com.fitbuddy.ai_service.responses.ApiResponse;
import com.fitbuddy.ai_service.services.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<List<RecommendationDto>>> getRecommendationsByUserId(@PathVariable String userId) {
        List<RecommendationDto> recommendationDtos = recommendationService.findByUserId(userId);
        ApiResponse<List<RecommendationDto>> response = ApiResponse.<List<RecommendationDto>>builder()
                .success(true)
                .data(recommendationDtos)
                .message("Recommendations fetched successfully")
                .error(null)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/activities/{activityId}")
    public ResponseEntity<ApiResponse<RecommendationDto>> getRecommendationByActivityId(@PathVariable String activityId) {
        RecommendationDto recommendationDto = recommendationService.findByActivityId(activityId);
        ApiResponse<RecommendationDto> response = ApiResponse.<RecommendationDto>builder()
                .success(true)
                .data(recommendationDto)
                .message("Recommendation fetched successfully")
                .error(null)
                .build();
        return ResponseEntity.ok(response);
    }
}
