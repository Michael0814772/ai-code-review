package com.michael.ai_code_review.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.michael.ai_code_review.dto.response.openAiResponseDto.error.OpenAiErrorResp;
import com.michael.ai_code_review.dto.response.openAiResponseDto.success.OpenAiSuccessResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OpenAiResponseMapperTest {

    @Mock
    private ObjectMapper objectMapper;

    private OpenAiResponseMapper openAiResponseMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        openAiResponseMapper = new OpenAiResponseMapper(objectMapper);
    }

    @Test
    void mapSuccessResponse_shouldConvertHashMapToOpenAiSuccessResponse() {
        // Arrange
        HashMap<String, Object> input = new HashMap<>();
        OpenAiSuccessResponse expectedOutput = new OpenAiSuccessResponse();
        when(objectMapper.convertValue(input, OpenAiSuccessResponse.class)).thenReturn(expectedOutput);

        // Act
        OpenAiSuccessResponse result = openAiResponseMapper.mapSuccessResponse(input);

        // Assert
        assertEquals(expectedOutput, result);
        verify(objectMapper).convertValue(input, OpenAiSuccessResponse.class);
    }

    @Test
    void mapErrorResponse_shouldConvertHashMapToOpenAiErrorResp() {
        // Arrange
        HashMap<String, Object> input = new HashMap<>();
        OpenAiErrorResp expectedOutput = new OpenAiErrorResp();
        when(objectMapper.convertValue(input, OpenAiErrorResp.class)).thenReturn(expectedOutput);

        // Act
        OpenAiErrorResp result = openAiResponseMapper.mapErrorResponse(input);

        // Assert
        assertEquals(expectedOutput, result);
        verify(objectMapper).convertValue(input, OpenAiErrorResp.class);
    }

    @Test
    void extractScoreAndRecommendation_shouldParseJsonStringToScoreAndRecommendationMapper() throws JsonProcessingException {
        // Arrange
        String input = "{ \"score\": 85, \"recommendation\": \"Good job!\" }";
        ScoreAndRecommendationMapper expectedOutput = new ScoreAndRecommendationMapper();
        when(objectMapper.readValue(input, ScoreAndRecommendationMapper.class)).thenReturn(expectedOutput);

        // Act
        ScoreAndRecommendationMapper result = openAiResponseMapper.extractScoreAndRecommendation(input);

        // Assert
        assertEquals(expectedOutput, result);
        verify(objectMapper).readValue(input, ScoreAndRecommendationMapper.class);
    }

    @Test
    void extractScoreAndRecommendation_shouldThrowJsonProcessingException_whenInvalidJson() throws JsonProcessingException {
        // Arrange
        String input = "invalid json";
        when(objectMapper.readValue(input, ScoreAndRecommendationMapper.class)).thenThrow(JsonProcessingException.class);

        // Act & Assert
        assertThrows(JsonProcessingException.class, () -> openAiResponseMapper.extractScoreAndRecommendation(input));
        verify(objectMapper).readValue(input, ScoreAndRecommendationMapper.class);
    }

    @Test
    void writeAsString_shouldConvertObjectToJsonString() throws JsonProcessingException {
        // Arrange
        String input = "test input";
        String expectedOutput = "{\"key\":\"value\"}";
        when(objectMapper.writeValueAsString(input)).thenReturn(expectedOutput);

        // Act
        String result = openAiResponseMapper.writeAsString(input);

        // Assert
        assertEquals(expectedOutput, result);
        verify(objectMapper).writeValueAsString(input);
    }

    @Test
    void writeAsString_shouldThrowJsonProcessingException_whenSerializationFails() throws JsonProcessingException {
        // Arrange
        String input = "test input";
        when(objectMapper.writeValueAsString(input)).thenThrow(JsonProcessingException.class);

        // Act & Assert
        assertThrows(JsonProcessingException.class, () -> openAiResponseMapper.writeAsString(input));
        verify(objectMapper).writeValueAsString(input);
    }
}