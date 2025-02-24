package com.michael.ai_code_review.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michael.ai_code_review.properties.OpenAiRequestProperties;
import com.michael.ai_code_review.dto.genericResponse.Content;
import com.michael.ai_code_review.dto.genericResponse.GenericResponse;
import com.michael.ai_code_review.dto.genericResponse.ResponseData;
import com.michael.ai_code_review.dto.request.openAiRequestDto.OpenAiMessageDto;
import com.michael.ai_code_review.dto.request.aiRequest.CodeReviewRequestDto;
import com.michael.ai_code_review.dto.request.openAiRequestDto.OpenAiRequest;
import com.michael.ai_code_review.dto.response.openAiResponseDto.error.OpenAiErrorResp;
import com.michael.ai_code_review.dto.response.openAiResponseDto.success.OpenAiSuccessResponse;
import com.michael.ai_code_review.service.service.CodeReviewService;
import com.michael.ai_code_review.utils.UtilsClass;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    public ResponseEntity<?> reviewCode(CodeReviewRequestDto reviewRequestDto) {
        log.info("request: {}", reviewRequestDto);

        GenericResponse genericResponse = new GenericResponse();
        OpenAiRequest openAiRequest = buildOpenAiRequest(reviewRequestDto);

        HashMap<String, Boolean> apiCallContainsError = new HashMap<>();
        apiCallContainsError.put(CONTAINS_ERROR.getMessage(), false);

        Object openAiCall = utilsClass.getOpenAiCall(openAiRequest, openAiRequestProperties, apiCallContainsError);

        if (apiCallContainsError.get(CONTAINS_ERROR.getMessage())) {
            OpenAiErrorResp openAiErrorResp = objectMapper.convertValue(openAiCall, OpenAiErrorResp.class);
            log.error("Error gotten - {}", openAiErrorResp.getError().getMessage());
            genericResponse.setCode(ERROR_FETCHING_DATA.getCode());
            genericResponse.setMessage(ERROR_FETCHING_DATA.getMessage());

            return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        OpenAiSuccessResponse openAiSuccessResponse = objectMapper.convertValue(openAiCall, OpenAiSuccessResponse.class);
        log.info("openAiSuccessResponse - {}", openAiSuccessResponse);

        ResponseData responseData = buildResponseDataMethod(openAiSuccessResponse);

        genericResponse.setCode(SUCCESSFUL_RESPONSE.getCode());
        genericResponse.setMessage(SUCCESSFUL_RESPONSE.getMessage());
        genericResponse.setDatas(responseData);

        return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseData buildResponseDataMethod(OpenAiSuccessResponse openAiSuccessResponse) {

        ResponseData responseData = new ResponseData();

        List<Content> contents = openAiSuccessResponse.getChoices().stream()
                .map(choice -> new Content(choice.getMessage().getContent()))
                .toList();
        responseData.setContent(contents);
        return responseData;
    }

    private OpenAiRequest buildOpenAiRequest(CodeReviewRequestDto reviewRequestDto) {

        List<OpenAiMessageDto> modifiedMessages = reviewRequestDto.getMessages().stream()
                .map(msg -> new OpenAiMessageDto(msg.getContent())) // Add constant "role"
                .toList();

        OpenAiRequest openAiRequest = new OpenAiRequest();
        openAiRequest.setMessages(modifiedMessages);
        openAiRequest.setStore(openAiRequestProperties.getStore());
        openAiRequest.setModel(openAiRequestProperties.getModel());

        return openAiRequest;
    }
}
