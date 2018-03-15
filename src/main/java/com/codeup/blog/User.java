/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.blog;

import com.codeup.infrastructure.events.CanRecordEvents;
import com.codeup.infrastructure.events.Event;
import com.codeup.security.UserInformation;
import com.codeup.security.UserSignedUp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
@Entity @Table(name = "users")
public class User implements Serializable, CanRecordEvents {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String email;

    @Transient
    private List<Event> events = new ArrayList<>();

    public static User signUp(UserInformation information, PasswordEncoder encoder) {
        return new User(
            information.getUsername(),
            information.getEmail(),
            encoder.encode(information.getPassword())
        );
    }

    private User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        recordThat(new UserSignedUp(username, email, new Date()));
    }

    public User(User user) {
        id = user.id;
        username = user.username;
        password = user.password;
        email = user.email;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object anotherUser) {
        return anotherUser != null && anotherUser instanceof User && ((User) anotherUser).id == id;
    }

    @Override
    public void recordThat(Event userSignedUp) {
        events.add(userSignedUp);
    }

    @Override
    public Iterable<Event> events() {
        return events;
    }
}
