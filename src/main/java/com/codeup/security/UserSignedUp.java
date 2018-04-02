/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.security;

import com.codeup.infrastructure.events.Event;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter @NoArgsConstructor
public class UserSignedUp implements Event {
    private String name = "UserSignedUp";
    private Date occurredOn;
    private String username;
    private String email;

    public UserSignedUp(String username, String email, Date occurredOn) {
        this.username = username;
        this.email = email;
        this.occurredOn = occurredOn;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Date getOccurredOn() {
        return occurredOn;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
