package com.epam.rd.izh.validations;

import com.epam.rd.izh.annotations.DateMatches;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

@Component
public class DateValidator implements ConstraintValidator<DateMatches, String> {
    @Override
    public void initialize(DateMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
        return (validateDate(date));
    }

    private boolean validateDate(String date) {
        LocalDate nowDate = LocalDate.now();
        LocalDate parseDate = LocalDate.parse(date);

        return Period.between(parseDate, nowDate).getYears() >= 18;
    }
}
