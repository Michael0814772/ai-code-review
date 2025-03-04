package com.michael.ai_code_review.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomizedException {

    private String responseMsg;
    private int responseCode;
}
