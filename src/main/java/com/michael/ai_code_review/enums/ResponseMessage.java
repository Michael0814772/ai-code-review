package com.michael.ai_code_review.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public enum ResponseMessage {

    ERROR_FETCHING_DATA(500, "Error fetching data", "There was an error encountered while calling openai api", LocalDateTime.now()),
    DATA_ALREADY_EXIST(409, "Duplicate data", "Data with the same reference id already exist", LocalDateTime.now()),
    FAILURE(400, "Failure", " ", LocalDateTime.now()),
    INTERNAL_SERVER_ERROR(500, "Internal server error", " ", LocalDateTime.now()),
    UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED.value(), "Internal server error", " ", LocalDateTime.now()),
    WRONG_URL(500, "Incomplete request", " ", LocalDateTime.now()),
    SUCCESSFUL_RESPONSE(200, "Successful", "Data fetched successfully", LocalDateTime.now());

    private final int code;
    private final String message;
    private final String description;
    private final LocalDateTime date;

    ResponseMessage(int code, String message, String description, LocalDateTime date) {
        this.code = code;
        this.message = message;
        this.description = description;
        this.date = date;
    }
}

