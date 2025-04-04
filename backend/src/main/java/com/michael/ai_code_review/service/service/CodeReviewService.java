package com.michael.ai_code_review.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.michael.ai_code_review.dto.request.aiRequest.CodeReviewRequestDto;
import org.springframework.http.ResponseEntity;

public interface CodeReviewService {

    ResponseEntity<?> reviewCode(CodeReviewRequestDto code) throws JsonProcessingException;
}
