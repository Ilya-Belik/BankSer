package com.example.bankcards.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "transfers")
@Getter
@Setter
@NoArgsConstructor
public class TransferEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "from_card_id", nullable = false)
    private CardEntity fromCard;

    @ManyToOne
    @JoinColumn(name = "to_card_id", nullable = false)
    private CardEntity toCard;

    @NotNull
    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(length = 20)
    private String status;
}