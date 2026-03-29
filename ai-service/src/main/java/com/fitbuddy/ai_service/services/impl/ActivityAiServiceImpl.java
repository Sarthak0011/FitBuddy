package com.fitbuddy.ai_service.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitbuddy.ai_service.dtos.ActivityEvent;
import com.fitbuddy.ai_service.models.Recommendation;
import com.fitbuddy.ai_service.services.ActivityAiService;
import com.fitbuddy.ai_service.services.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityAiServiceImpl implements ActivityAiService {

    private final AiService aiService;

    @Override
    public Recommendation generateRecommendation(ActivityEvent activity) {
        String prompt = createPromptForActivity(activity);
        String aiResponse = aiService.getRecommendations(prompt);
//        log.info("RESPONSE FROM AI SERVER: {}", aiResponse);
        Recommendation recommendation = processAiResponse(activity, aiResponse);
        return recommendation;
    }

    private Recommendation processAiResponse(ActivityEvent activity, String aiResponse) {
        if (aiResponse == null || aiResponse.trim().isEmpty()) {
            log.warn("Received empty or null response from AI service for activity {}", activity.getActivityId());
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(aiResponse);
            JsonNode textNode = rootNode.path("candidates")
                    .path(0)
                    .path("content")
                    .path("parts")
                    .path(0)
                    .path("text");

            if (textNode.isMissingNode() || textNode.asText().isEmpty()) {
                log.warn("Gemini response is empty or invalid format: {}", aiResponse);
                return null;
            }

            String jsonContent = textNode.asText()
                    .replaceAll("```json\\n", "")
                    .replaceAll("\\n```", "")
                    .trim();
//            log.info("RESPONSE: {}", jsonContent);

            JsonNode responseNode = objectMapper.readTree(jsonContent);
            JsonNode analysisNode = responseNode.path("analysis");
            StringBuilder fullAnalysis = new StringBuilder();
            processAnalysisSection(fullAnalysis, analysisNode, "overall", "Overall: ");
            processAnalysisSection(fullAnalysis, analysisNode, "pace", "Pace: ");
            processAnalysisSection(fullAnalysis, analysisNode, "heartRate", "Heart Rate: ");
            processAnalysisSection(fullAnalysis, analysisNode, "caloriesBurned", "Calories Burned: ");

            List<String> improvements = extractImprovements(responseNode.path("improvements"));
            List<String> suggestions = extractSuggestions(responseNode.path("suggestions"));
            List<String> safetyGuidelines = extractSafety(responseNode.path("safety"));

            return Recommendation.builder()
                    .activityId(activity.getActivityId())
                    .userId(activity.getUserId())
                    .recommendation(fullAnalysis.toString())
                    .improvements(improvements)
                    .suggestions(suggestions)
                    .safety(safetyGuidelines)
                    .build();
        } catch (Exception ex) {
            log.error("Error parsing AI Response: {}", ex.getMessage());
        }
        return null;
    }

    private void processAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix) {
        if(!analysisNode.path(key).isMissingNode()) {
            fullAnalysis.append(prefix)
                    .append(analysisNode.path(key).asText())
                    .append("\n\n");
        }
    }

    private List<String> extractSafety(JsonNode safetyNode) {
        List<String> safetyGuidelines = new ArrayList<>();
        if(safetyNode.isArray()) {
            safetyNode.forEach(safety -> safetyGuidelines.add(safety.asText()));
        }
        return safetyGuidelines.isEmpty() ?
                Collections.singletonList("No safety guidelines found.") :
                safetyGuidelines;
    }

    private List<String> extractSuggestions(JsonNode suggestionsNode) {
        List<String> suggestions = new ArrayList<>();
        if(suggestionsNode.isArray()) {
            suggestionsNode.forEach(suggestion -> {
                String workout = suggestion.path("workout").asText();
                String description = suggestion.path("description").asText();
                suggestions.add(String.format("%s: %s", workout, description));
            });
        }
        return suggestions.isEmpty() ?
                Collections.singletonList("No suggestions found.") :
                suggestions;
    }

    private List<String> extractImprovements(JsonNode improvementsNode) {
        List<String> improvements = new ArrayList<>();
        if(improvementsNode.isArray()) {
            improvementsNode.forEach(improvement -> {
                String area = improvement.path("area").asText();
                String recommendation = improvement.path("recommendation").asText();
                improvements.add(String.format("%s: %s", area, recommendation));
            });
        }
        return improvements.isEmpty() ?
                Collections.singletonList("No improvements found.") :
                improvements;
    }



    private String createPromptForActivity(ActivityEvent activity) {
        return String.format("""
        Analyze this fitness activity and provide detailed recommendations in the following EXACT JSON format:
        {
          "analysis": {
            "overall": "Overall analysis here",
            "pace": "Pace analysis here",
            "heartRate": "Heart rate analysis here",
            "caloriesBurned": "Calories analysis here"
          },
          "improvements": [
            {
              "area": "Area name",
              "recommendation": "Detailed recommendation"
            }
          ],
          "suggestions": [
            {
              "workout": "Workout name",
              "description": "Detailed workout description"
            }
          ],
          "safety": [
            "Safety point 1",
            "Safety point 2"
          ]
        }

        Analyze this activity:
        Activity Type: %s
        Duration: %d minutes
        Calories Burned: %d
        Additional Metrics: %s
        
        Provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.
        Ensure the response follows the EXACT JSON format shown above.
        """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalMatrics()
        );
    }
}
