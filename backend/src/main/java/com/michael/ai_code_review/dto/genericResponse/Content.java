package com.michael.ai_code_review.dto.genericResponse;

import lombok.Data;

import java.util.List;

@Data
public class Content {

    private int score;
    private List<String> recommendations;
}
