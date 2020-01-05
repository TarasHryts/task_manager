package com.djamware.mongodbsecurity.exception;

public class IncorrectEmailSendException extends RuntimeException {
    public IncorrectEmailSendException(String message) {
        super(message);
    }
}
