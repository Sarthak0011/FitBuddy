package com.fitbuddy.ai_service.services.impl;

import com.fitbuddy.ai_service.dtos.RecommendationDto;
import com.fitbuddy.ai_service.models.Recommendation;
import com.fitbuddy.ai_service.repositories.RecommendationRepository;
import com.fitbuddy.ai_service.services.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;

    @Override
    public List<RecommendationDto> findByUserId(String userId) {
        List<Recommendation> recommendations = recommendationRepository.findByUserId(userId);

        return recommendations
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public RecommendationDto findByActivityId(String activityId) {
        Recommendation recommendation = recommendationRepository.findByActivityId(activityId)
                .orElseThrow(() -> new RuntimeException("Recommendation not found with with activityId: " + activityId));
        return convertToDto(recommendation);
    }

    private RecommendationDto convertToDto(Recommendation recommendation) {
        return RecommendationDto.builder()
                .id(recommendation.getId())
                .activityId(recommendation.getActivityId())
                .userId(recommendation.getUserId())
                .recommendation(recommendation.getRecommendation())
                .suggestions(recommendation.getSuggestions())
                .improvements(recommendation.getImprovements())
                .safety(recommendation.getSafety())
                .createdAt(recommendation.getCreatedAt())
                .updateAt(recommendation.getUpdatedAt())
                .build();
    }
}
