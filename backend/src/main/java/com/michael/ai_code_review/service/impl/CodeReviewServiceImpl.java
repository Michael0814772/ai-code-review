package com.michael.ai_code_review.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.michael.ai_code_review.dto.genericResponse.Content;
import com.michael.ai_code_review.dto.genericResponse.GenericResponse;
import com.michael.ai_code_review.dto.request.aiRequest.CodeReviewRequestDto;
import com.michael.ai_code_review.dto.request.openAiRequestDto.OpenAiRequest;
import com.michael.ai_code_review.dto.response.openAiResponseDto.error.OpenAiErrorResp;
import com.michael.ai_code_review.dto.response.openAiResponseDto.success.OpenAiSuccessResponse;
import com.michael.ai_code_review.mapper.OpenAiResponseMapper;
import com.michael.ai_code_review.mapper.ScoreAndRecommendationMapper;
import com.michael.ai_code_review.service.service.CodeReviewService;
import com.michael.ai_code_review.utils.openai.OpenAiClient;
import com.michael.ai_code_review.utils.openai.OpenAiRequestFactory;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.michael.ai_code_review.enums.ResponseMessage.ERROR_FETCHING_DATA;
import static com.michael.ai_code_review.enums.ResponseMessage.SUCCESSFUL_RESPONSE;

@Service
@Slf4j
@RequiredArgsConstructor
public class CodeReviewServiceImpl implements CodeReviewService {

    private final OpenAiClient openAiClient;
    private final OpenAiRequestFactory openAiRequestFactory;
    private final OpenAiResponseMapper openAiResponseMapper;

    @Override
    public ResponseEntity<?> reviewCode(CodeReviewRequestDto reviewRequestDto) throws JsonProcessingException {
        log.info("request: {}", reviewRequestDto);

        OpenAiRequest openAiRequest = openAiRequestFactory.createRequest(reviewRequestDto);

        HashMap<String, Object> openAiResponse = openAiClient.callOpenAi(openAiRequest);

        // Check for errors
        if (openAiResponse.containsKey("error")) {
            return handleErrorResponse(openAiResponse);
        }

        OpenAiSuccessResponse openAiSuccessResponse = openAiResponseMapper.mapSuccessResponse(openAiResponse);
        String message = openAiResponseMapper.writeAsString(openAiSuccessResponse.getChoices().get(0).getMessage().getContent());

        // Clean up JSON response
        message = methodToCleanResponse(message);

        ScoreAndRecommendationMapper scoreAndRecommendationMapper = openAiResponseMapper.extractScoreAndRecommendation(message);

        return buildSuccessResponse(scoreAndRecommendationMapper);
    }

    private ResponseEntity<?> handleErrorResponse(HashMap<String, Object> openAiResponse) {
        OpenAiErrorResp errorResponse = openAiResponseMapper.mapErrorResponse(openAiResponse);
        log.error("Error received - {}", errorResponse.getError().getMessage());

        GenericResponse genericResponse = new GenericResponse(ERROR_FETCHING_DATA.getCode(), ERROR_FETCHING_DATA.getMessage());
        return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> buildSuccessResponse(ScoreAndRecommendationMapper scoreAndRecommendationMapper) {

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setCode(SUCCESSFUL_RESPONSE.getCode());
        genericResponse.setMessage(SUCCESSFUL_RESPONSE.getMessage());
        genericResponse.setData(buildContent(scoreAndRecommendationMapper));
        log.info("genericResponse - {}", genericResponse);

        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    private String methodToCleanResponse(String message) {

        if (StringUtils.isBlank(message)) {
            return "";
        }
        return message.trim()
                .replaceAll("^\"|\"$", "")  // Remove leading and trailing quotes
                .replace("\\n", "")         // Remove unnecessary newlines
                .replace("\\", "");
    }

    private Object buildContent(ScoreAndRecommendationMapper score) {
        Content content = new Content();
        content.setScore(score.getScore());
        content.setRecommendations(score.getRecommendations());
        return content;
    }
}
