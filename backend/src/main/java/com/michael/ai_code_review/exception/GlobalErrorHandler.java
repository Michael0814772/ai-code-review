package com.michael.ai_code_review.exception;

import com.michael.ai_code_review.exception.exceptionMethod.*;
import com.michael.ai_code_review.exception.methodNotValid.ValidationErrorResponse;
import com.michael.ai_code_review.exception.methodNotValid.Violation;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.Arrays;

import static com.michael.ai_code_review.enums.ResponseMessage.*;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {

    @ExceptionHandler(MyCustomizedException.class)
    public ResponseEntity<CustomizedException> myCustomizedException(final MyCustomizedException ex) {
        CustomizedException customizedException = new CustomizedException();
        customizedException.setResponseMsg(ex.getMessage());
        customizedException.setResponseCode(FAILURE.getCode());
        return new ResponseEntity<>(customizedException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MyUserIsNotFoundException.class)
    public ResponseEntity<UserIsNotFoundException> UserIsNotFoundException(final MyUserIsNotFoundException ex) {
        UserIsNotFoundException customizedException = new UserIsNotFoundException();
        customizedException.setMessage(ex.getMessage());
        return new ResponseEntity<>(customizedException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<CustomizedException> myDuplicateException(final DuplicateException ex) {
        CustomizedException customizedException = new CustomizedException();
        customizedException.setResponseMsg(DATA_ALREADY_EXIST.getMessage());
        customizedException.setResponseCode(DATA_ALREADY_EXIST.getCode());
        return new ResponseEntity<>(customizedException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CustomizedException> UnauthorizedException(final UnauthorizedException ex) {
        CustomizedException customizedException = new CustomizedException();
        customizedException.setResponseMsg(ex.getMessage());
        customizedException.setResponseCode(FAILURE.getCode());
        return new ResponseEntity<>(customizedException, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MyCustomException.class)
    public ResponseEntity<CustomException> accountNotFound(final MyCustomException ex) {
        log.info(ex.toString());
        CustomException customException = new CustomException();
        customException.setMessage(ex.getMessage());
        customException.setCode(FAILURE.getCode());
        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<CustomException> myMalformedJwtException(final MalformedJwtException ex) {
        log.info(ex.toString());
        CustomException customException = new CustomException();
        customException.setMessage(ex.getMessage());
        customException.setCode(FAILURE.getCode());
        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomException> myIllegalArgumentException(final IllegalArgumentException ex) {
        log.info(ex.toString());
        CustomException customException = new CustomException();
        customException.setMessage("Error while processing your request");
        customException.setCode(INTERNAL_SERVER_ERROR.getCode());
        return new ResponseEntity<>(customException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<CustomException> NullPointerException(final NullPointerException ex) {
        log.info(ex.toString());
        CustomException customException = new CustomException();
        customException.setMessage(ex.getMessage());
        customException.setCode(INTERNAL_SERVER_ERROR.getCode());
        return new ResponseEntity<>(customException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IdNotExistException.class)
    public ResponseEntity<CustomException> isDoesNotExist(final IdNotExistException ex) {
        log.info(ex.toString());
        CustomException customException = new CustomException();
        customException.setMessage(ex.getMessage());
        customException.setCode(FAILURE.getCode());
        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IdExistException.class)
    public ResponseEntity<CustomException> idExistException(final IdExistException ex) {
        log.info(ex.toString());
        CustomException customException = new CustomException();
        customException.setMessage(ex.getMessage());
        customException.setCode(FAILURE.getCode());
        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        log.info(ex.toString());
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();

        for (ConstraintViolation<?> fieldError : ex.getConstraintViolations()) {
            errorResponse.getErrors().add(
                    new Violation(fieldError.getPropertyPath().toString(), fieldError.getMessage()));
        }

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomException> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.info(ex.toString());
        String[] error = new String[0];
        ArrayList<String> errors = new ArrayList<String>(Arrays.asList(error));

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(fieldError.getField() + ":" + fieldError.getDefaultMessage());
        }
        CustomException customException = new CustomException();
        customException.setMessage(errors.toString());
        customException.setCode(FAILURE.getCode());

        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomException> handleDataIntegrityViolationException(final DataIntegrityViolationException ex) {
        log.info("cause: " + ex.toString());
        CustomException customException = new CustomException();
        customException.setMessage(DATA_ALREADY_EXIST.getMessage());
        customException.setCode(DATA_ALREADY_EXIST.getCode());
        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomException> handleHttpMessageNotReadableException(final HttpMessageNotReadableException ex) {
        log.info(ex.toString());
        CustomException customException = new CustomException();
        customException.setMessage(ex.getMessage());
        customException.setCode(FAILURE.getCode());
        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<CustomException> handleInvalidDataAccessResourceUsageException(final InvalidDataAccessResourceUsageException ex) {
        log.info(ex.toString());
        CustomException customException = new CustomException();
        customException.setMessage(ex.getMessage());
        customException.setCode(FAILURE.getCode());
        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<CustomException> handleDataAccessResourceFailureException(final DataAccessResourceFailureException ex) {
        log.info(ex.toString());
        CustomException customException = new CustomException();
        customException.setMessage(ex.getMessage());
        customException.setCode(FAILURE.getCode());
        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<CustomException> handleNumberFormatException(final NumberFormatException ex) {
        log.info(ex.toString());
        CustomException customException = new CustomException();
        customException.setMessage(ex.getMessage());
        customException.setCode(FAILURE.getCode());
        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<CustomException> handleMissingRequestHeaderException(final MissingRequestHeaderException ex) {
        log.info(ex.toString());
        CustomException customException = new CustomException();
        customException.setMessage(ex.getMessage());
        customException.setCode(FAILURE.getCode());
        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<CustomException> handleInternalServerException(final InternalServerException ex) {
        log.info(ex.toString());
        CustomException customException = new CustomException();
        customException.setMessage(ex.getMessage());
        customException.setCode(INTERNAL_SERVER_ERROR.getCode());
        return new ResponseEntity<>(customException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<CustomException> handleNoResourceFoundException(final NoResourceFoundException ex) {
        log.info(ex.toString());
        CustomException customException = new CustomException();
        customException.setMessage(WRONG_URL.getMessage());
        customException.setCode(WRONG_URL.getCode());
        return new ResponseEntity<>(customException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<CustomException> handleException(final Exception ex) {
//        log.info(ex.toString());
//        CustomException customException = new CustomException();
//        customException.setMessage(ex.getMessage());
//        customException.setCode(INTERNAL_SERVER_ERROR.getCode());
//        return new ResponseEntity<>(customException, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(NoClassDefFoundError.class)
    public ResponseEntity<CustomException> handleNoClassDefFoundError(final NoClassDefFoundError ex) {
        log.info(ex.toString());
        CustomException customException = new CustomException();
        customException.setMessage(ex.getMessage());
        customException.setCode(INTERNAL_SERVER_ERROR.getCode());
        return new ResponseEntity<>(customException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomException> handleSecurityException(Exception exception) {
        CustomException errorDetail = new CustomException();

        exception.printStackTrace();

        if (exception instanceof BadCredentialsException) {
            errorDetail.setMessage("The username or password is incorrect");
            errorDetail.setCode(INTERNAL_SERVER_ERROR.getCode());
        }

        if (exception instanceof AccountStatusException) {
            errorDetail.setMessage("The account is locked");
            errorDetail.setCode(INTERNAL_SERVER_ERROR.getCode());
        }

        if (exception instanceof AccessDeniedException) {
            errorDetail.setMessage("You are not authorized to access this resource");
            errorDetail.setCode(INTERNAL_SERVER_ERROR.getCode());
        }

        if (exception instanceof SignatureException) {
            errorDetail.setMessage("Signature is invalid");
            errorDetail.setCode(INTERNAL_SERVER_ERROR.getCode());
        }

        if (exception instanceof ExpiredJwtException) {
            errorDetail.setMessage("Token has expired");
            errorDetail.setCode(UNAUTHORIZED_ERROR.getCode());
            return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
