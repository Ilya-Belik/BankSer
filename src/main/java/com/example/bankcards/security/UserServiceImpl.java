package com.example.bankcards.security;

import com.example.bankcards.dto.UserCreateRequest;
import com.example.bankcards.entity.Role;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.exception.PersonNotFoundException;
import com.example.bankcards.util.UserMapper;
import lombok.RequiredArgsConstructor;
import com.example.bankcards.entity.User;
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
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(PersonNotFoundException::new);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return username -> getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + username));
    }

    @Override
    public User getCurrentUser() {
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
    public User createNewUser(UserCreateRequest request) {
        User user = userMapper.toEntityFromDto(request);
        System.out.println(user);
        user.setPassword(jwtService.passwordEncoder().encode(request.getPassword()));
        var currentUser = getCurrentUser();
        if (currentUser != null && currentUser.getRole().equals(Role.ADMIN)) {
            user.setRole(request.getRole());
        } else {
            user.setRole(Role.USER);
        }
        System.out.println(user);
        return userRepository.save(user);
    }


    @Transactional
    @Override
    public void delete(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(PersonNotFoundException::new);
        userRepository.delete(user);
    }

    @Transactional
    @Override
    public User update(User updatedPerson) {
        User existingUser = userRepository.findById(updatedPerson.getId())
                .orElseThrow(PersonNotFoundException::new);

        if (updatedPerson.getUsername() != null) {
            existingUser.setUsername(updatedPerson.getUsername());
        }

        if (updatedPerson.getPassword() != null) {
            String encodedPassword = jwtService.passwordEncoder().encode(updatedPerson.getPassword());
            existingUser.setPassword(encodedPassword);
        }

        if (updatedPerson.getRole() != null) {
            existingUser.setRole(updatedPerson.getRole());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
