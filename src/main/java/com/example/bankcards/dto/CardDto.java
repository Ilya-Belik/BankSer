package com.example.bankcards.dto;

import com.example.bankcards.entity.CardStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "card list")
public class CardDto {
    private String username;
    private String cardNumber;
    private Date validityPeriod;
    private BigDecimal balance;
    private CardStatusEnum status;
}