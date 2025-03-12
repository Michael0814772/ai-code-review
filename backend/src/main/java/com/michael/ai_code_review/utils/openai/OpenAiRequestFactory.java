package com.michael.ai_code_review.utils.openai;

import com.michael.ai_code_review.dto.request.aiRequest.CodeReviewRequestDto;
import com.michael.ai_code_review.dto.request.openAiRequestDto.OpenAiMessageDto;
import com.michael.ai_code_review.dto.request.openAiRequestDto.OpenAiRequest;
import com.michael.ai_code_review.properties.OpenAiRequestProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OpenAiRequestFactory {

    private final OpenAiRequestProperties openAiRequestProperties;

    public OpenAiRequest createRequest(CodeReviewRequestDto reviewRequestDto) {

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
