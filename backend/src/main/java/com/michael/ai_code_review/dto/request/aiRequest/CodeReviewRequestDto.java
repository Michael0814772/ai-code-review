package com.michael.ai_code_review.dto.request.aiRequest;

import jakarta.validation.Valid;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Data
@Validated
public class CodeReviewRequestDto {

    @NotBlank
    @Valid
    private List<MessageDto> messages  = new ArrayList<>();

    @NotBlank
    private String requestId;
}
