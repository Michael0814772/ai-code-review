package com.michael.ai_code_review.enums;

import lombok.Getter;

@Getter
public enum HashMapConst {

    CONTAINS_ERROR("containsError");

    private final String message;

    HashMapConst(String message) {
        this.message = message;
    }
}
