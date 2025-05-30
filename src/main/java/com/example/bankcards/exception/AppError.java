package com.example.bankcards.exception;

import lombok.Data;

import java.util.Date;

@Data
public class AppError {
    private int status;
    private String message;
    private Date timestamp;
}