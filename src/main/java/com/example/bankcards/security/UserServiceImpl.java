package com.example.bankcards.security;

import com.example.bankcards.dto.UserCreateRequest;
import com.example.bankcards.entity.RoleEnum;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.exception.PersonNotFoundException;
import com.example.bankcards.util.UserMapper;
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
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity findUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(PersonNotFoundException::new);
    }

    @Override
    public Optional<UserEntity> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return username -> getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + username));
    }

    @Override
    public UserEntity getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }
        var username = authentication.getName();
        return getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Текущий пользователь не найден: " + username));
    }

    @Transactional
    @Override
    public UserEntity createNewUser(UserCreateRequest request) {
        UserEntity userEntity = userMapper.toEntityFromDto(request);
        System.out.println(userEntity);
        userEntity.setPassword(jwtService.passwordEncoder().encode(request.getPassword()));
        var currentUser = getCurrentUser();
        if (currentUser != null && currentUser.getRoleEnum().equals(RoleEnum.ADMIN)) {
            userEntity.setRoleEnum(request.getRoleEnum());
        } else {
            userEntity.setRoleEnum(RoleEnum.USER);
        }
        System.out.println(userEntity);
        return userRepository.save(userEntity);
    }


    @Transactional
    @Override
    public void delete(UUID id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(PersonNotFoundException::new);
        userRepository.delete(userEntity);
    }

    @Transactional
    @Override
    public UserEntity update(UserEntity updatedPerson) {
        UserEntity existingUserEntity = userRepository.findById(updatedPerson.getId())
                .orElseThrow(PersonNotFoundException::new);

        if (updatedPerson.getUsername() != null) {
            existingUserEntity.setUsername(updatedPerson.getUsername());
        }

        if (updatedPerson.getPassword() != null) {
            String encodedPassword = jwtService.passwordEncoder().encode(updatedPerson.getPassword());
            existingUserEntity.setPassword(encodedPassword);
        }

        if (updatedPerson.getRoleEnum() != null) {
            existingUserEntity.setRoleEnum(updatedPerson.getRoleEnum());
        }

        return userRepository.save(existingUserEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
