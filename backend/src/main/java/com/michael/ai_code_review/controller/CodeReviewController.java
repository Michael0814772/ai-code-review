package com.michael.ai_code_review.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.michael.ai_code_review.dto.request.aiRequest.CodeReviewRequestDto;
import com.michael.ai_code_review.dto.request.user.LoginUserDto;
import com.michael.ai_code_review.dto.request.user.RegisterUserDto;
import com.michael.ai_code_review.model.UsersTokenTable;
import com.michael.ai_code_review.service.service.CodeReviewService;
import com.michael.ai_code_review.service.service.UserManagementService;
import com.michael.ai_code_review.service.service.UsersTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class CodeReviewController {

    private final CodeReviewService reviewService;
    private final UsersTokenService usersTokenService;
    private final UserManagementService userManagementService;

    @PostMapping(path = "/generate", headers = "X-API-VERSION=1")
    public ResponseEntity<?> reviewCode(@Valid @RequestBody CodeReviewRequestDto code) throws JsonProcessingException {
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

    @GetMapping(path = "/me", headers = "X-API-VERSION=1")
    public ResponseEntity<UsersTokenTable> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UsersTokenTable currentUser = (UsersTokenTable) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping(path = "/fetch-users", headers = "X-API-VERSION=1")
    public ResponseEntity<List<UsersTokenTable>> allUsers() {
        return userManagementService.allUsers();
    }

    @GetMapping(path = "/")
    public String home() {
        return "Welcome to AI Code Review API!";
    }
}
