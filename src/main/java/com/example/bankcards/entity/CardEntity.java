package com.example.bankcards.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
public class CardEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @NotNull
    @Column(name = "masked_number", nullable = false, length = 19)
    private String maskedNumber;

    @NotNull
    @Column(name = "validity_period", nullable = false)
    private Date validityPeriod;

    @NotNull
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private CardStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(name = "created_at")
    private Timestamp createdAt;
}