package com.example.bankcards.mapper;

import com.example.bankcards.dto.UserResponse;
import com.example.bankcards.entity.RoleName;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role",
            expression = "java( mapMainRole(entity) )")
    @Mapping(target = "token", ignore = true)   // заполним позже
    UserResponse toDto(UserEntity entity);

    default RoleName mapMainRole(UserEntity entity) {
        return entity.getRoles().stream()
                .findFirst()
                .map(RoleEntity::getName)
                .orElse(null);
    }
}
