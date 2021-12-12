package com.epam.rd.izh.validations;

import com.epam.rd.izh.annotations.DateMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

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

        Period period = Period.between(nowDate, parseDate);
        return (Math.abs(period.getDays()) / 12) >= 18;
    }
}
