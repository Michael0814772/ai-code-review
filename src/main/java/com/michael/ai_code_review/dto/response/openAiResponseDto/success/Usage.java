package com.michael.ai_code_review.dto.response.openAiResponseDto.success;

import lombok.Data;

@Data
public class Usage {

    public int prompt_tokens;
    public int completion_tokens;
    public int total_tokens;
    public PromptTokensDetails prompt_tokens_details;
    public CompletionTokensDetails completion_tokens_details;
}
