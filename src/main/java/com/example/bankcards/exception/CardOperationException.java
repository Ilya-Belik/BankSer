package com.example.bankcards.exception;

public class CardOperationException extends RuntimeException {
    private final int status;

    public CardOperationException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}