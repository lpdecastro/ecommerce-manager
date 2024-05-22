package com.lpdecastro.ecommerce.exception;

import com.lpdecastro.ecommerce.dto.ErrorResponseDto;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class AppExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponseDto> handleAppException(AppException ex) {
        HttpStatus httpStatus = ex.getHttpStatus();

        String message = messageSource.getMessage(ex.getErrorMessage().getCode(),
                ex.getErrorParams(), Locale.getDefault());

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(httpStatus, message);

        return new ResponseEntity<>(errorResponseDto, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(httpStatus);

        BindingResult bindingResult = ex.getBindingResult();
        if (bindingResult.hasFieldErrors()) {
            Map<String, String> fields = new HashMap<>();
            bindingResult.getFieldErrors().forEach(field ->
                    fields.put(field.getField(), field.getDefaultMessage()));

            errorResponseDto.setFields(fields);
            errorResponseDto.setMessage("Some fields are invalid");
        } else if (bindingResult.getGlobalError() != null) {
            errorResponseDto.setMessage(bindingResult.getGlobalError().getDefaultMessage());
        }

        return new ResponseEntity<>(errorResponseDto, httpStatus);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolationException(ConstraintViolationException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        Map<String, String> fields = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
                fields.put(violation.getPropertyPath().toString(), violation.getMessage()));

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(httpStatus, fields, "Some fields are invalid");

        return new ResponseEntity<>(errorResponseDto, httpStatus);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(httpStatus, ex.getMessage());
        return new ResponseEntity<>(errorResponseDto, httpStatus);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessDeniedException(AccessDeniedException ex) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(httpStatus, ex.getMessage());
        return new ResponseEntity<>(errorResponseDto, httpStatus);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleBadCredentialsException(BadCredentialsException ex) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(httpStatus, ex.getMessage());
        return new ResponseEntity<>(errorResponseDto, httpStatus);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(httpStatus, ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponseDto);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentTypeMismatchEx(MethodArgumentTypeMismatchException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(httpStatus, ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponseDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(httpStatus, ex.getMessage());
        return new ResponseEntity<>(errorResponseDto, httpStatus);
    }
}
