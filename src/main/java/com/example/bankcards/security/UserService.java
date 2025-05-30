package com.example.bankcards.security;

import com.example.bankcards.dto.UserCreateRequest;
import com.example.bankcards.dto.UserResponse;
import com.example.bankcards.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService extends UserDetailsService {

    List<User> getAll();

    User findUserById(UUID id);

    User createNewUser(UserCreateRequest request);

    void delete(UUID id);

    User update(User updatedUser);

    Optional<User> getByUsername(String username);

    UserDetailsService userDetailsService();

    User getCurrentUser();
}
