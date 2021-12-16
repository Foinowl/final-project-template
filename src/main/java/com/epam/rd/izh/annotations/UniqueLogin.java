package com.epam.rd.izh.annotations;

import com.epam.rd.izh.validations.LoginValidator;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LoginValidator.class)
@Documented
public @interface UniqueLogin {
    String message() default "This login already exist";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};
}
