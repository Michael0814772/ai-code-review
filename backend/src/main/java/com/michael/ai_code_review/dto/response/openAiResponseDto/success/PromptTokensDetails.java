package com.michael.ai_code_review.dto.response.openAiResponseDto.success;

import lombok.Data;

@Data
public class PromptTokensDetails {

    public int cached_tokens;
    public int audio_tokens;
}
