package org.example.bank_management_system.Exception;

import org.example.bank_management_system.Api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> handleApiException(ApiException exception) {
        return ResponseEntity.status(400)
                .body(new ApiResponse(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationException(
            MethodArgumentNotValidException exception) {

        FieldError error = exception.getBindingResult().getFieldError();

        String message = error != null
                ? error.getDefaultMessage()
                : "Validation failed";

        return ResponseEntity.status(400)
                .body(new ApiResponse(message));
    }
}