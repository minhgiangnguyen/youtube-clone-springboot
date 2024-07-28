package com.petproject.youtubeclone.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.petproject.youtubeclone.repositories.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return userRepository.emailExists(email);
    }
}
