package com.michael.ai_code_review.dto.genericResponse;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GenericResponse {

    private int code;
    private String message;
    private LocalDateTime date = LocalDateTime.now();
    private Object data;
}
