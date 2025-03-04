package com.michael.ai_code_review.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class RegisterUserDto {

    @NotBlank(message = "email is required")
    @Pattern(regexp = ".*@.*", message = "invalid email address")
    private String email;

    @NotBlank(message = "password is required")
    private String password;

    @NotBlank(message = "fullName is required")
    private String fullName;

    @NotBlank(message = "username is required")
    private String username;
}
