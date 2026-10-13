package org.example.bank_management_system.Exception;

public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}