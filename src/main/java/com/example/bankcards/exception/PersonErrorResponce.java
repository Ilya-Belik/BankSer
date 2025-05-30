package com.example.bankcards.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonErrorResponce {
    private String message;
    private long timestamp;

    public PersonErrorResponce(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

}
