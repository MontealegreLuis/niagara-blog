/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter @Setter
@Entity @Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue
    private int id;

    @Column(nullable = false)
    @NotBlank(message = "Enter a username")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Your password cannot be empty")
    @Size(min = 8, message = "Your password should have at least 8 characters")
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @Email(message = "Enter a valid email address")
    @NotBlank(message = "Enter an email")
    private String email;

    public User(User user) {
        id = user.id;
        username = user.username;
        password = user.password;
        email = user.email;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void encodePassword(PasswordEncoder encoder) {
        password = encoder.encode(password);
    }

    @Override
    public boolean equals(Object anotherUser) {
        return anotherUser != null && anotherUser instanceof User && ((User) anotherUser).id == id;
    }
}
