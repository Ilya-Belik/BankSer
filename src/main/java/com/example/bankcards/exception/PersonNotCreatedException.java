package com.example.bankcards.exception;

public class PersonNotCreatedException extends RuntimeException{
    public PersonNotCreatedException(String message) {
        super(message);
    }
}
