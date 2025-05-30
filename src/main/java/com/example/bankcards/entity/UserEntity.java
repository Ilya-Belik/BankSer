package com.example.bankcards.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Table(name = "users")
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue()
    @Column(name = "id")
    private UUID id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 50, message = "Псевдоним не должен быть короче 2 или длиннее 50 символов")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Поле пароль обязательно к заполнению")
    @Size(min = 5, max = 200, message = "Пароль должен быть не короче 5 или длиннее 50 символов, состоять должен из латинских символов разного регистра")
    @Column(name = "password")
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CardEntity> cardEntities;


    @Override
    public String toString() {
        return String.format("Person{%s\n%s\n%s\n%s}\n", this.id, this.username, this.password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roleEnum.name()));
    }
}
