package com.michael.ai_code_review.dto.request.aiRequest;

import jakarta.validation.Valid;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.wildfly.common.annotation.NotNull;

import java.util.ArrayList;

@Data
@Validated
public class CodeReviewRequestDto {

    @NotNull
    @Valid
    private ArrayList<MessageDto> messages  = new ArrayList<>();

    @NotBlank
    private String requestId;
}
