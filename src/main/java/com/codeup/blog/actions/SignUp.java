/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.blog.actions;

import com.codeup.blog.User;
import com.codeup.infrastructure.events.Publisher;
import com.codeup.security.UserInformation;
import com.codeup.security.Users;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUp {
    private Users users;
    private PasswordEncoder encoder;
    private Publisher publisher;

    public SignUp(Users users, PasswordEncoder encoder, Publisher publisher) {
        this.users = users;
        this.encoder = encoder;
        this.publisher = publisher;
    }

    public void signUpWith(UserInformation userInformation) {
        User user = User.signUp(userInformation, encoder);
        users.save(user);
        publisher.publish(user.events());
    }
}
