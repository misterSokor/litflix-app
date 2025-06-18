package com.store.litflix.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        /*
        errors: []
        timestamp: date and time
        status: 2xx, 4xx, 5xx
         */

        //we can either create a separate object to hold the error response or use a map
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("status", status.value());

        List<String> errors = ex
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(this::getErrorMessage)
                .toList();
        responseBody.put("errors", errors);
        return new ResponseEntity<>(responseBody, headers, status);
    }

    private String getErrorMessage(ObjectError error) {
        if (error instanceof FieldError fieldError) {
            return fieldError.getField() + ": " + fieldError.getDefaultMessage();
        }
        return error.getDefaultMessage();
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<Object> handleRegistrationException(
            RegistrationException ex, WebRequest request) {

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("status", HttpStatus.CONFLICT.value());
        responseBody.put("error", "Registration Failed");
        responseBody.put("message", ex.getMessage());
        responseBody.put("path",
                request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<Object> handleCartItemNotFoundException(
            CartItemNotFoundException ex, WebRequest request) {

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("status", HttpStatus.NOT_FOUND.value());
        responseBody.put("error", "Cart Item Not Found");
        responseBody.put("message", ex.getMessage());
        responseBody.put("path",
                request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartItemAccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(
            CartItemAccessDeniedException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.FORBIDDEN.value());
        body.put("error", "Access Denied");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }
}
