package com.michael.ai_code_review.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.michael.ai_code_review.model.UsersTokenTable;
import com.michael.ai_code_review.repository.UsersTokenRepository;
import com.michael.ai_code_review.service.service.UserManagementService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {

    private final UsersTokenRepository usersTokenRepository;
    
    @Override
    public ResponseEntity<List<UsersTokenTable>> allUsers() {

        List<UsersTokenTable> users = new ArrayList<>(usersTokenRepository.findAll());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
}
