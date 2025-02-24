package com.michael.ai_code_review.dto.genericResponse;

import lombok.Data;

@Data
public class Content {

    private String message;

    public Content(String message) {
        this.message = message;
    }
}
