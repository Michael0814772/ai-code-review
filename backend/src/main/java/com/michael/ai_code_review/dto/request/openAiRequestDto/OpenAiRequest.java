package com.michael.ai_code_review.dto.request.openAiRequestDto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OpenAiRequest {

    public String model;
    public boolean store;
    public List<OpenAiMessageDto> messages = new ArrayList<>();
}
