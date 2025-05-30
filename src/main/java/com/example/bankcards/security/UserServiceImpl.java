package com.example.bankcards.security;

import com.example.bankcards.dto.UserCreateRequest;
import com.example.bankcards.entity.RoleEntity;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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
    RoleEntity role = roleRepository.findByName(request.getRoleEnum())
            .orElseThrow(() -> new IllegalArgumentException("Role not found"));

    UserEntity user = UserEntity.builder()
            .username(request.getUsername())
            .roles(Set.of(role))
            .build();

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


public UserDetailsService userDetailsService() {
    return this::loadUserByUsername;
}

@Override
public UserEntity getCurrentUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserEntity user) {
        return user;
    }
    throw new UsernameNotFoundException("Пользователь не найден в контексте безопасности");
}
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + username));
    }
}