package com.michael.ai_code_review.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomException {
    private String message;
    private int code;
    private LocalDateTime date = LocalDateTime.now();
}
