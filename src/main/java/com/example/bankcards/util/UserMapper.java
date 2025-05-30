package com.example.bankcards.util;

import com.example.bankcards.dto.UserCreateRequest;
import com.example.bankcards.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    //@Mapping(target = "role", ignore = true)
    UserEntity toEntityFromDto(UserCreateRequest userCreateRequest);
}
