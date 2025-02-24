package com.michael.ai_code_review.dto.genericResponse;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseData {

    private List<Content> content = new ArrayList<>();
}
