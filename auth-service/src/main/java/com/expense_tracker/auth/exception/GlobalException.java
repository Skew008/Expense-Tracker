package com.expense_tracker.auth.exception;

import jakarta.persistence.EntityExistsException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BadRequestException.class, BadCredentialsException.class, IllegalArgumentException.class})
    public ResponseEntity<Object> handleBadRequest(Exception e, WebRequest webRequest) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<Object> handleEntityAlreadyExist(Exception e, WebRequest webRequest) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, webRequest);
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<Object> handleNotFound(Exception e, WebRequest webRequest) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }
}
