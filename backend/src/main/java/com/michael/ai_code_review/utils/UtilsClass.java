package com.michael.ai_code_review.utils;

import com.michael.ai_code_review.properties.OpenAiRequestProperties;
import com.michael.ai_code_review.dto.request.openAiRequestDto.OpenAiRequest;
import com.michael.ai_code_review.exception.exceptionMethod.MyCustomException;
import com.michael.ai_code_review.httpCall.OpenAiCall;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static com.michael.ai_code_review.enums.ResponseMessage.ERROR_FETCHING_DATA;

@Component
@Slf4j
@RequiredArgsConstructor
public class UtilsClass {

    private final OpenAiCall openAiCall;

    public HashMap<String, Object> getOpenAiCall(OpenAiRequest openAiRequest, OpenAiRequestProperties openAiRequestProperties) {

        HashMap<String, Object> response;

        log.info("request - {}", openAiRequest);

        try {
            response =  openAiCall.openAiCall(openAiRequest, openAiRequestProperties);
            log.info("response gotten - {}", response);
            return response;
        } catch (Exception e) {
            log.error("Error gotten from calling openapi - {}", e.getMessage());
            throw new MyCustomException(ERROR_FETCHING_DATA.getMessage());
        }
    }
}
