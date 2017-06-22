package com.celesio.filetransferagent.validation;

import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.reflect.FieldUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class FlagValidator implements ConstraintValidator<FlagValidated, Object> {

    @Override
    public void initialize(FlagValidated constraintAnnotation) {
        //Nothing to initialize at the moment
    }

    @Override
    public boolean isValid(Object validatedObject, ConstraintValidatorContext context) {
        if (validatedObject == null) {
            return true;
        }
        MutableBoolean allValid = new MutableBoolean(true);
        FieldUtils.getFieldsListWithAnnotation(validatedObject.getClass(), Flag.class).forEach(flagField -> {
            String flagName = flagField.getAnnotation(Flag.class).value();
            boolean flagEnabled = isFlagEnabled(validatedObject, flagField);

            FieldUtils.getFieldsListWithAnnotation(validatedObject.getClass(), RequiredIfFlag.class).forEach(field -> {
                if (!flagEnabled || !field.getAnnotation(RequiredIfFlag.class).flagName().equals(flagName)) {
                    return;
                }
                checkField(validatedObject, field, context, allValid);
            });

            FieldUtils.getFieldsListWithAnnotation(validatedObject.getClass(), NotRequiredIfFlag.class).forEach(field -> {
                if (flagEnabled || !field.getAnnotation(NotRequiredIfFlag.class).flagName().equals(flagName)) {
                    return;
                }
                checkField(validatedObject, field, context, allValid);
            });
        });
        return allValid.getValue();
    }

    private void checkField(Object validatedObject, Field field,
                            ConstraintValidatorContext context, MutableBoolean allValid) {
        try {
            if (FieldUtils.readField(field, validatedObject, true) != null) {
                return;
            }
            context.buildConstraintViolationWithTemplate("Field is required")
                    .addPropertyNode(field.getName())
                    .addConstraintViolation();
            allValid.setFalse();
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private boolean isFlagEnabled(Object validatedObject, Field flagField) {
        try {
            String fieldValue = String.valueOf(FieldUtils.readField(flagField, validatedObject, true));
            String expectedValue = flagField.getAnnotation(Flag.class).flagValue();
            return expectedValue.equals(fieldValue);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
