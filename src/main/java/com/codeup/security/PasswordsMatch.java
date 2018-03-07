/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.security;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordsMatchValidator.class)
@Documented
public @interface PasswordsMatch {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "Passwords must match";

    String first();

    String second();
}
