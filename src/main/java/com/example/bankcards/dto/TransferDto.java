package com.example.bankcards.dto;

import com.example.bankcards.entity.TransferStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "transfer list")
public class TransferDto {
    private UUID id;
    private UUID fromCardId;
    private UUID toCardId;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private TransferStatusEnum status;
}

