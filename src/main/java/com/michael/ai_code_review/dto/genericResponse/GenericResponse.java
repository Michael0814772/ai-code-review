package com.michael.ai_code_review.dto.genericResponse;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GenericResponse {

    private String code;
    private String message;
    private LocalDateTime date = LocalDateTime.now();
    private ResponseData datas;
    private Object data;
}
