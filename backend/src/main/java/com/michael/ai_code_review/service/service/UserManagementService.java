package com.michael.ai_code_review.service.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.michael.ai_code_review.model.UsersTokenTable;

public interface UserManagementService {
    ResponseEntity<List<UsersTokenTable>> allUsers();
}
