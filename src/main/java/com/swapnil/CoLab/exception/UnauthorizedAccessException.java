package com.swapnil.CoLab.exception;

public class UnauthorizedAccessException extends CustomException{
    public UnauthorizedAccessException(String message) {
        super(message);
    }

    public UnauthorizedAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
