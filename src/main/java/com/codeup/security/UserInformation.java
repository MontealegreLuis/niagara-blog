/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.security;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@PasswordsMatch(message = "Your passwords do not match", first = "password", second = "passwordConfirm")
public class UserInformation {
    @NotNull(message = "All users must have a username")
    @NotBlank(message = "Enter a username")
    private String username;

    @NotNull(message = "All users must have a password")
    @NotBlank(message = "Your password cannot be empty")
    @Size(min = 8, message = "Your password should have at least 8 characters")
    private String password;
    private String passwordConfirm;

    @NotNull(message = "All users must have an email")
    @NotBlank(message = "Enter an email")
    @Email(message = "Enter a valid email address")
    private String email;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
