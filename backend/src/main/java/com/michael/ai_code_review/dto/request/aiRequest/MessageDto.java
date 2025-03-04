package com.michael.ai_code_review.dto.request.aiRequest;

import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
public class MessageDto {

    @NotBlank
    public String content;
}
