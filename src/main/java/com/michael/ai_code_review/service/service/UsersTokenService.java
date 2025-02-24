package com.michael.ai_code_review.service.service;

import com.michael.ai_code_review.dto.request.user.LoginUserDto;
import com.michael.ai_code_review.dto.request.user.RegisterUserDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface UsersTokenService {

    ResponseEntity<?> registerUser(@Valid RegisterUserDto registerUserDto);

    ResponseEntity<?> loginUser(@Valid LoginUserDto loginUserDto);
}
