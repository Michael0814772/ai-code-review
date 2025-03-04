package com.michael.ai_code_review.dto.response.openAiResponseDto.success;

import lombok.Data;

@Data
public class Message {

    public String role;
    public String content;
    public Object refusal;
}
