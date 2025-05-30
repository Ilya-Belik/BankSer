package com.example.bankcards.controller;

import com.example.bankcards.dto.UserCreateRequest;
import com.example.bankcards.dto.UserResponse;
import com.example.bankcards.entity.User;
import com.example.bankcards.security.AuthServiceImpl;
import com.example.bankcards.security.UserService;
import com.example.bankcards.exception.PersonErrorResponce;
import com.example.bankcards.exception.PersonNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("user/{id}")
    @PreAuthorize("hasRole('USER')")
    public User getUserById(@PathVariable UUID id) {
        return userService.findUserById(id);
    }

    @PostMapping("user/{id}/update")
    @PreAuthorize("hasRole('USER')")
    public User update(@PathVariable UUID id) {
        return userService.findUserById(id);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponce> handleException(PersonNotFoundException e){
        PersonErrorResponce response = new PersonErrorResponce(
                "Нет пользователя с таким ID",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}