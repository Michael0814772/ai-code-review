package com.michael.ai_code_review.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class UserIsNotFoundException {
    private String message;
    private String code = "404";
    private LocalDateTime date = LocalDateTime.now();
}
