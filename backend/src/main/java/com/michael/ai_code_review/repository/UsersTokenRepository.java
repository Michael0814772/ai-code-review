package com.michael.ai_code_review.repository;

import com.michael.ai_code_review.model.UsersTokenTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersTokenRepository extends JpaRepository<UsersTokenTable, Long> {

    @Query(value = "select s from UsersTokenTable s where s.username = :username OR s.email = :email")
    UsersTokenTable findByUsernameAndEmail(@Param("username") String username, @Param("email") String email);

    @Query(value = "select s from UsersTokenTable s where s.username = :username")
    Optional<UsersTokenTable> findByUsername(@Param("username") String username);
}
