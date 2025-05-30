package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Ответ при регистрации или авторизации")
public class UserResponse {
    @Schema(description = "Идентификатор пользователя")
    private UUID id;

    @Schema(description = "Имя пользователя")
    private String username;

    @Schema(description = "Роль")
    private String role;

    @Schema(description = "JWT токен доступа")
    private String token;
}