package com.example.bankcards.util;

import com.example.bankcards.entity.User;
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
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User person = (User) target;
        Optional<User> existingPerson = personService.getByUsername(person.getUsername());
        if (existingPerson.isPresent()) {
            errors.rejectValue("username", "", "Пользователь с таким именем уже существует");
        }
    }
}
