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
        responseBody.put("status", HttpStatus.BAD_REQUEST);

        List<String> errors = ex
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> getErrorMessage(error))
                .toList();
        responseBody.put("errors", errors);
        return new ResponseEntity<>(responseBody, headers, status);
    }

    private String getErrorMessage(ObjectError error) {
        if (error instanceof FieldError) {

            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            return field + ": " + message;
        }
        return error.getDefaultMessage();
    }
}
