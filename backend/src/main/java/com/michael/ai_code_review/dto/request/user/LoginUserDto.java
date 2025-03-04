package com.michael.ai_code_review.dto.request.user;

import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class LoginUserDto {

    private String email;

    @NotBlank
    private String password;

    private String username;
}
