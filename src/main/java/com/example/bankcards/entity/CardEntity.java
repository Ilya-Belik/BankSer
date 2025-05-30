package com.example.bankcards.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Table(name = "cards")
@Entity
@Getter
@Setter
public class CardEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull
    @Column(name = "card_number")
    private String cardNumber;

    @NotNull
    @Column(name = "validity_period")
    private Date validityPeriod;

    @NotNull
    @Column(name = "balance")
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CardStatusEnum status;

    public UserEntity getUserEntity() {
        return this.userEntity;
    }
}