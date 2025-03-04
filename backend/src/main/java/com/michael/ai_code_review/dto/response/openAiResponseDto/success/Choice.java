package com.michael.ai_code_review.dto.response.openAiResponseDto.success;

import lombok.Data;

@Data
public class Choice {

    public int index;
    public Message message;
    public Object logprobs;
    public String finish_reason;
}
