package com.swapnil.CoLab.exception;

public class TaskAlreadyExistsException extends CustomException{
    public TaskAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskAlreadyExistsException(String message) {
        super(message);
    }
}
