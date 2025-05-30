package com.example.bankcards.dto;

import lombok.Data;

@Data
public class CardUpdateRequest {
    private String type;
    private String status;
}