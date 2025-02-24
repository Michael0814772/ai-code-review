package com.michael.ai_code_review.dto.request.openAiRequestDto;

import lombok.Data;

@Data
public class OpenAiMessageDto {

    public String role = "user";
    public String content;

    // Constructor
    public OpenAiMessageDto(String content) {
        this.content = content;
    }
}
