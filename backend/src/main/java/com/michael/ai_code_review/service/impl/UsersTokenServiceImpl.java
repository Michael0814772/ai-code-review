package com.michael.ai_code_review.service.impl;

import com.michael.ai_code_review.dto.genericResponse.GenericResponse;
import com.michael.ai_code_review.dto.request.user.LoginUserDto;
import com.michael.ai_code_review.dto.request.user.RegisterUserDto;
import com.michael.ai_code_review.dto.response.user.LoginResponseDto;
import com.michael.ai_code_review.exception.exceptionMethod.MyCustomException;
import com.michael.ai_code_review.exception.exceptionMethod.MyUserIsNotFoundException;
import com.michael.ai_code_review.model.UsersTokenTable;
import com.michael.ai_code_review.properties.JwtWebTokenProperties;
import com.michael.ai_code_review.repository.UsersTokenRepository;
import com.michael.ai_code_review.service.security.JwtService;
import com.michael.ai_code_review.service.service.UsersTokenService;
import com.michael.ai_code_review.utils.SavingUtilsClass;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.michael.ai_code_review.enums.ResponseMessage.SUCCESSFUL_RESPONSE;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersTokenServiceImpl implements UsersTokenService {

    private final UsersTokenRepository usersTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SavingUtilsClass savingUtilsClass;
    private final JwtService jwtService;
    private final JwtWebTokenProperties jwtWebTokenProperties;

    @Override
    public ResponseEntity<?> registerUser(RegisterUserDto registerUserDto) {
        log.info("register usersTokenTable method...");

        methodToValidateIfUsernameOrEmailAlreadyExist(registerUserDto);

        UsersTokenTable usersTokenTable = saveUsersDetails(registerUserDto);

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setData(usersTokenTable);
        genericResponse.setCode(SUCCESSFUL_RESPONSE.getCode());
        genericResponse.setMessage(SUCCESSFUL_RESPONSE.getMessage());

        return new ResponseEntity<>(genericResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> loginUser(LoginUserDto loginUserDto) {
        log.info("login user method...");

        validateRequiredFields(loginUserDto);

        UsersTokenTable findByUserNameOrEmail = usersTokenRepository.findByUsernameAndEmail(loginUserDto.getUsername(), loginUserDto.getEmail());

        validIfNull(findByUserNameOrEmail);

        String username = findByUserNameOrEmail.getUsername();
        log.info("username - {}", username);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        loginUserDto.getPassword()
                )
        );

        String jwtToken = jwtService.generateToken(findByUserNameOrEmail);
        log.info("jwtToken - {}", jwtToken);

        LoginResponseDto loginResponseDto = new LoginResponseDto(jwtToken, jwtWebTokenProperties.getExpirationTime());

        GenericResponse genericResponse = setGenericResponse(loginResponseDto);

        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }


    private GenericResponse setGenericResponse(LoginResponseDto loginResponseDto) {

        GenericResponse genericResponse = new GenericResponse();

        genericResponse.setData(loginResponseDto);
        genericResponse.setCode(SUCCESSFUL_RESPONSE.getCode());
        genericResponse.setMessage(SUCCESSFUL_RESPONSE.getMessage());
        return genericResponse;
    }

    private void validIfNull(UsersTokenTable findByUserNameOrEmail) {

        if (findByUserNameOrEmail == null) {
            throw new MyUserIsNotFoundException("user not found");
        }
    }

    private void validateRequiredFields(LoginUserDto loginUserDto) {

        if ((loginUserDto.getUsername() == null || loginUserDto.getUsername().trim().isEmpty())
                && (loginUserDto.getEmail() == null || loginUserDto.getEmail().trim().isEmpty())) {
            throw new MyCustomException("username or email is required");
        }
    }

    private UsersTokenTable saveUsersDetails(RegisterUserDto registerUserDto) {
        UsersTokenTable usersTokenTable = new UsersTokenTable();

        String password = passwordEncoder.encode(registerUserDto.getPassword());

        usersTokenTable.setEmail(registerUserDto.getEmail());
        usersTokenTable.setUsername(registerUserDto.getUsername());
        usersTokenTable.setFullName(registerUserDto.getFullName());
        usersTokenTable.setPassword(password);

        usersTokenTable = savingUtilsClass.saveDetails(usersTokenTable);

        return usersTokenTable;
    }

    private void methodToValidateIfUsernameOrEmailAlreadyExist(RegisterUserDto registerUserDto) {

        UsersTokenTable findByUsernameAndEmail = usersTokenRepository.findByUsernameAndEmail(registerUserDto.getUsername(), registerUserDto.getEmail());

        String value = registerUserDto.getUsername() == null ? "email" : "username";

        if (findByUsernameAndEmail != null) {
            throw new MyCustomException(value + " already exist");
        }
    }
}
