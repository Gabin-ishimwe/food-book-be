package com.backend.app.foodbook.auth.exception;

public class UserAccessDenied extends Exception {
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public UserAccessDenied(String message) {
        super(message);
    }
}
