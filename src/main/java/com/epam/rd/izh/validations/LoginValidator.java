package com.epam.rd.izh.validations;

import com.epam.rd.izh.annotations.UniqueLogin;
import com.epam.rd.izh.service.UserService;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class LoginValidator implements ConstraintValidator<UniqueLogin, String> {
    final private UserService userService;

    public LoginValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(UniqueLogin constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return (validateLogin(s));
    }

    private boolean validateLogin(String login) {
        if (userService.isLoginAvailable(login)) {
            return true;
        }
        return false;

    }
}
