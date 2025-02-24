package com.michael.ai_code_review.dto.response.openAiResponseDto.success;

import lombok.Data;

@Data
public class CompletionTokensDetails {

    public int reasoning_tokens;
    public int audio_tokens;
    public int accepted_prediction_tokens;
    public int rejected_prediction_tokens;
}
