package com.backend.app.foodbook.auth.exception;

public class UserExistsException extends Exception {
    public UserExistsException(String message) {
        super(message);
    }

}
