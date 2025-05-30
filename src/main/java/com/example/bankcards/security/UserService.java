package com.example.bankcards.security;

import com.example.bankcards.dto.UserCreateRequest;
import com.example.bankcards.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserService extends UserDetailsService {

    List<UserEntity> getAll();

    UserEntity findUserById(UUID id);

    UserEntity createNewUser(UserCreateRequest request);

    void delete(UUID id);

    UserEntity update(UserEntity updatedUserEntity);

    Optional<UserEntity> getByUsername(String username);

    UserEntity getCurrentUser();

}
