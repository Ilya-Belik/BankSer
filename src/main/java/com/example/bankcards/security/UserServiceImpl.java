package com.example.bankcards.security;

import com.example.bankcards.dto.UserCreateRequest;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.exception.PersonNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

private final UserRepository userRepository;

@Override
public List<UserEntity> getAll() {
    return userRepository.findAll();
}

@Override
public UserEntity findUserById(UUID id) {
    return userRepository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
}

@Override
public UserEntity createNewUser(UserCreateRequest request) {
    UserEntity user = new UserEntity();
    user.setUsername(request.getUsername());
    user.setPassword(request.getPassword());
    return userRepository.save(user);
}

@Override
public void delete(UUID id) {
    userRepository.deleteById(id);
}

@Override
public UserEntity update(UserEntity updatedUserEntity) {
    return userRepository.save(updatedUserEntity);
}

@Override
public Optional<UserEntity> getByUsername(String username) {
    return userRepository.findByUsername(username);
}

@Override
public UserDetailsService userDetailsService() {
    return this::loadUserByUsername;
}

@Override
public UserEntity getCurrentUser() {
    // здесь реализация зависит от твоего `SecurityContextHolder`
    return null;
}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + username));
    }
}