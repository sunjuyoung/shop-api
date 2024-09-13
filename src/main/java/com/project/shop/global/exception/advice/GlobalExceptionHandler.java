package com.project.shop.global.exception.advice;

import com.project.shop.global.exception.*;
import com.project.shop.global.exception.enums.ExceptionCode;
import com.project.shop.global.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("---handleMethodArgumentNotValidException---");

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errors = fieldErrors.stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        final ErrorResponse response =
                ErrorResponse.of(ExceptionCode.INVALID_INPUT_VALUE, errors.get(0));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundCategory.class)
    public ResponseEntity<ErrorResponse> notFoundCategoryException(NotFoundCategory ex) {
        log.error("---notFoundCategoryException---");
        final ErrorResponse response = ErrorResponse.of(ex.getExceptionCode());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> productNotFoundExceptionException(ProductNotFoundException ex) {
        log.error("---productNotFoundExceptionException---");
        final ErrorResponse response = ErrorResponse.of(ex.getExceptionCode());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<ErrorResponse> PasswordNotMatchException(PasswordNotMatchException ex) {
        log.error("---PasswordNotMatchException---");
        final ErrorResponse response = ErrorResponse.of(ex.getExceptionCode());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentAmountException.class)
    public ResponseEntity<ErrorResponse> PaymentAmountException(PaymentAmountException ex) {
        log.error("---PaymentAmountException---");
        final ErrorResponse response = ErrorResponse.of(ex.getExceptionCode(),ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentAlreadyException.class)
    public ResponseEntity<ErrorResponse> PaymentAlreadyException(PaymentAlreadyException ex) {
        log.error("---PaymentAlreadyException---");
        final ErrorResponse response = ErrorResponse.of(ex.getExceptionCode());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
