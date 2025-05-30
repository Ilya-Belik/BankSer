package com.example.bankcards.util;

import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.security.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonValidator implements Validator {

    private final UserService personService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserEntity person = (UserEntity) target;
        Optional<UserEntity> existingPerson = personService.getByUsername(person.getUsername());
        if (existingPerson.isPresent()) {
            errors.rejectValue("username", "", "Пользователь с таким именем уже существует");
        }
    }
}
