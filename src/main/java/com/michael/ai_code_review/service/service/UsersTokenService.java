package com.michael.ai_code_review.service.service;

import com.michael.ai_code_review.dto.request.user.LoginUserDto;
import com.michael.ai_code_review.dto.request.user.RegisterUserDto;
import com.michael.ai_code_review.model.UsersTokenTable;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsersTokenService {

    ResponseEntity<?> registerUser(@Valid RegisterUserDto registerUserDto);

    ResponseEntity<?> loginUser(@Valid LoginUserDto loginUserDto);

    ResponseEntity<List<UsersTokenTable>> allUsers();
}
