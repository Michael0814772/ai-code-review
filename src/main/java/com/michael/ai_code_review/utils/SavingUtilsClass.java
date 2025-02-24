package com.michael.ai_code_review.utils;

import com.michael.ai_code_review.exception.exceptionMethod.InternalServerException;
import com.michael.ai_code_review.model.UsersTokenTable;
import com.michael.ai_code_review.repository.UsersTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SavingUtilsClass {
    private final UsersTokenRepository usersTokenRepository;

    public UsersTokenTable saveDetails(UsersTokenTable usersTokenTable) {
        try {
            usersTokenTable = usersTokenRepository.save(usersTokenTable);
            log.info("usersToken Table - {}", usersTokenTable);
        } catch (Exception e) {
            throw new InternalServerException("Kindly try again later");
        }
        return usersTokenTable;
    }
}
