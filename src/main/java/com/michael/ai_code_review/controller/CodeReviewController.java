package com.michael.ai_code_review.controller;

import com.michael.ai_code_review.dto.request.user.LoginUserDto;
import com.michael.ai_code_review.dto.request.user.RegisterUserDto;
import com.michael.ai_code_review.dto.request.aiRequest.CodeReviewRequestDto;
import com.michael.ai_code_review.service.service.CodeReviewService;
import com.michael.ai_code_review.service.service.UsersTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class CodeReviewController {

    private final CodeReviewService reviewService;
    private final UsersTokenService usersTokenService;

    @PostMapping(path = "/generate", headers = "X-API-VERSION=1")
    public ResponseEntity<?> reviewCode(@Valid @RequestBody CodeReviewRequestDto code) {
        return reviewService.reviewCode(code);
    }

    @PostMapping(path = "/auth/register-user", headers = "X-API-VERSION=1")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) {
        return usersTokenService.registerUser(registerUserDto);
    }

    @PostMapping(path = "/auth/login-user", headers = "X-API-VERSION=1")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginUserDto loginUserDto) {
        return usersTokenService.loginUser(loginUserDto);
    }
}
