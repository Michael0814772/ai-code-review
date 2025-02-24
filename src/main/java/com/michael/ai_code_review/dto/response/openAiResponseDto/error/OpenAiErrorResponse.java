package com.michael.ai_code_review.dto.response.openAiResponseDto.error;

import lombok.Data;

@Data
public class OpenAiErrorResponse {

    public String message;
    public String type;
    public Object param;
    public String code;
}
