package com.michael.ai_code_review.utils.openai;

import com.michael.ai_code_review.dto.request.openAiRequestDto.OpenAiRequest;
import com.michael.ai_code_review.properties.OpenAiRequestProperties;
import com.michael.ai_code_review.utils.UtilsClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class OpenAiClient {

    private final OpenAiRequestProperties openAiRequestProperties;
    private final UtilsClass utilsClass;

    public HashMap<String, Object> callOpenAi(OpenAiRequest request) {
        return utilsClass.getOpenAiCall(request, openAiRequestProperties);
    }
}
