package com.michael.ai_code_review.dto.response.openAiResponseDto.success;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OpenAiSuccessResponse {

    public String id;
    public String object;
    public int created;
    public String model;
    public ArrayList<Choice> choices;
    public Usage usage;
    public String service_tier;
    public String system_fingerprint;
}
