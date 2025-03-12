package com.michael.ai_code_review.dto.genericResponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GenericResponse {

    private int code;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime date = LocalDateTime.now();
    private Object data;

    public GenericResponse() {
    }

    public GenericResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
