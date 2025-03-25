package com.michael.ai_code_review.dto.request.aiRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    @NotBlank
    public String content;

//    public MessageDto(String content) {
//        this.content = content;
//    }
}
