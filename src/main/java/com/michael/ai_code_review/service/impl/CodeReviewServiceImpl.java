package com.michael.ai_code_review.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.michael.ai_code_review.dto.genericResponse.Content;
import com.michael.ai_code_review.dto.genericResponse.GenericResponse;
import com.michael.ai_code_review.dto.request.aiRequest.CodeReviewRequestDto;
import com.michael.ai_code_review.dto.request.openAiRequestDto.OpenAiMessageDto;
import com.michael.ai_code_review.dto.request.openAiRequestDto.OpenAiRequest;
import com.michael.ai_code_review.dto.response.openAiResponseDto.error.OpenAiErrorResp;
import com.michael.ai_code_review.dto.response.openAiResponseDto.success.OpenAiSuccessResponse;
import com.michael.ai_code_review.mapper.ScoreAndRecommendationMapper;
import com.michael.ai_code_review.properties.OpenAiRequestProperties;
import com.michael.ai_code_review.service.service.CodeReviewService;
import com.michael.ai_code_review.utils.UtilsClass;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.michael.ai_code_review.enums.HashMapConst.CONTAINS_ERROR;
import static com.michael.ai_code_review.enums.ResponseMessage.ERROR_FETCHING_DATA;
import static com.michael.ai_code_review.enums.ResponseMessage.SUCCESSFUL_RESPONSE;

@Service
@Slf4j
@RequiredArgsConstructor
public class CodeReviewServiceImpl implements CodeReviewService {

    private final OpenAiRequestProperties openAiRequestProperties;
    private final UtilsClass utilsClass;
    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntity<?> reviewCode(CodeReviewRequestDto reviewRequestDto) throws JsonProcessingException {
        log.info("request: {}", reviewRequestDto);

        GenericResponse genericResponse = new GenericResponse();
        OpenAiRequest openAiRequest = buildOpenAiRequest(reviewRequestDto);

        HashMap<String, Boolean> apiCallContainsError = new HashMap<>();
        apiCallContainsError.put(CONTAINS_ERROR.getMessage(), false);

        HashMap<String, Object> openAiCall = utilsClass.getOpenAiCall(openAiRequest, openAiRequestProperties);

        checkErrorMethod(openAiCall, apiCallContainsError, genericResponse);

        if (apiCallContainsError.get(CONTAINS_ERROR.getMessage())) {
            return methodToBuildError(openAiCall, genericResponse);
        }

        OpenAiSuccessResponse openAiSuccessResponse = objectMapper.convertValue(openAiCall, OpenAiSuccessResponse.class);
        String message = objectMapper.writeValueAsString(openAiSuccessResponse.getChoices().get(0).getMessage().getContent());

        // Clean up JSON response
        message = methodToCleanResponse(message);

        ScoreAndRecommendationMapper scoreAndRecommendationMapper = objectMapper.readValue(message, ScoreAndRecommendationMapper.class);

        genericResponse.setCode(SUCCESSFUL_RESPONSE.getCode());
        genericResponse.setMessage(SUCCESSFUL_RESPONSE.getMessage());
        genericResponse.setData(buildContent(scoreAndRecommendationMapper));
        log.info("genericResponse - {}", genericResponse);

        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    private ResponseEntity<?> methodToBuildError(HashMap<String, Object> openAiCall, GenericResponse genericResponse) {
        OpenAiErrorResp openAiErrorResp = objectMapper.convertValue(openAiCall, OpenAiErrorResp.class);
        log.error("Error gotten - {}", openAiErrorResp.getError().getMessage());
        genericResponse.setCode(ERROR_FETCHING_DATA.getCode());
        genericResponse.setMessage(ERROR_FETCHING_DATA.getMessage());

        return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
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
        content.setRecommendation(score.getRecommendations());
        return content;
    }

    private void checkErrorMethod(HashMap<String, Object> openAiCall, HashMap<String, Boolean> apiCallContainsError, GenericResponse genericResponse) {

        if (openAiCall.containsKey("error")) {
            apiCallContainsError.put(CONTAINS_ERROR.getMessage(), true);
        }
    }

    private OpenAiRequest buildOpenAiRequest(CodeReviewRequestDto reviewRequestDto) {

        // Create the system message
        List<OpenAiMessageDto> messages = new ArrayList<>();
        messages.add(new OpenAiMessageDto(openAiRequestProperties.getRole(), openAiRequestProperties.getMessage()));

        // Convert user messages
        messages.addAll(reviewRequestDto.getMessages().stream()
                .map(msg -> new OpenAiMessageDto("user", openAiRequestProperties.getUserMessage() + msg.getContent()))
                .toList());

        OpenAiRequest openAiRequest = new OpenAiRequest();
        openAiRequest.setMessages(messages);
        openAiRequest.setStore(openAiRequestProperties.getStore());
        openAiRequest.setModel(openAiRequestProperties.getModel());

        return openAiRequest;
    }
}
