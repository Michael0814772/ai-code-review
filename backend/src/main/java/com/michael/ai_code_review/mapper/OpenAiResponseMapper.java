package com.michael.ai_code_review.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.michael.ai_code_review.dto.response.openAiResponseDto.error.OpenAiErrorResp;
import com.michael.ai_code_review.dto.response.openAiResponseDto.success.OpenAiSuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class OpenAiResponseMapper {

    private final ObjectMapper objectMapper;

    public OpenAiSuccessResponse mapSuccessResponse(HashMap<String, Object> openAiResponse) {
        return objectMapper.convertValue(openAiResponse, OpenAiSuccessResponse.class);
    }

    public OpenAiErrorResp mapErrorResponse(HashMap<String, Object> openAiResponse) {
        return objectMapper.convertValue(openAiResponse, OpenAiErrorResp.class);
    }

    public ScoreAndRecommendationMapper extractScoreAndRecommendation(String message) throws JsonProcessingException {
        return objectMapper.readValue(message, ScoreAndRecommendationMapper.class);
    }

    public String writeAsString(String openAiSuccessResponse) throws JsonProcessingException {
        return objectMapper.writeValueAsString(openAiSuccessResponse);
    }
}
