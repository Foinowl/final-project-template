
package com.epam.rd.izh.annotations;

import com.epam.rd.izh.validations.DateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
@Documented
public @interface DateMatches {
    String message() default "Date birth, the age must be over 18";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};
}