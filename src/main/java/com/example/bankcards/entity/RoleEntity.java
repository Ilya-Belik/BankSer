package com.example.bankcards.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class RoleEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;
}