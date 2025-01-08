package com.store.litflix.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            final Field firstField = value.getClass().getDeclaredField(firstFieldName);
            final Field secondField = value.getClass().getDeclaredField(secondFieldName);

            firstField.setAccessible(true);
            secondField.setAccessible(true);

            final Object firstObj = firstField.get(value);
            final Object secondObj = secondField.get(value);

            boolean isValidated = (firstObj == null && secondObj == null)
                                  || (firstObj != null
                                      && firstObj.equals(secondObj));

            if (!isValidated) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message)
                        .addPropertyNode(secondFieldName)
                        .addConstraintViolation();
            }
            return isValidated;
        } catch (final Exception ignore) {
            return false;
        }
    }
}
