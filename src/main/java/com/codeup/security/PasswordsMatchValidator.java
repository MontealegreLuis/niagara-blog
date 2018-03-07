/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.security;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(PasswordsMatch annotation) {
        firstFieldName = annotation.first();
        secondFieldName = annotation.second();
        message = annotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            BeanWrapperImpl wrapper = new BeanWrapperImpl(value);
            final Object password = wrapper.getPropertyValue(firstFieldName);
            final Object passwordConfirm = wrapper.getPropertyValue(secondFieldName);

            valid = !StringUtils.isEmpty(password)
                && !StringUtils.isEmpty(passwordConfirm)
                && password.equals(passwordConfirm);

        } catch (final Exception ignore) {
        }

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(secondFieldName)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
