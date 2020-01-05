package com.djamware.mongodbsecurity.exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String s) {
        super(s);
    }
}
