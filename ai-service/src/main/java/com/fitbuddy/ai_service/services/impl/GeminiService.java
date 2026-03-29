package com.fitbuddy.ai_service.services.impl;

import com.fitbuddy.ai_service.dtos.GeminiContent;
import com.fitbuddy.ai_service.dtos.GeminiPart;
import com.fitbuddy.ai_service.dtos.GeminiRequestDto;
import com.fitbuddy.ai_service.services.AiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class GeminiService implements AiService {

    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public String getRecommendations(String details) {
        GeminiPart part = new GeminiPart();
        part.setText(details);
        GeminiContent content = new GeminiContent();
        content.setParts(List.of(part));
        GeminiRequestDto request = new GeminiRequestDto();
        request.setContents(List.of(content));

        return webClient.post()
                .uri(geminiApiUrl)
                .header("Content-Type", "application/json")
                .header("x-goog-api-key", geminiApiKey)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
