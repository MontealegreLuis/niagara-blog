package com.codeup.controllers.security;

import com.codeup.blog.User;
import com.codeup.infrastructure.events.Publisher;
import com.codeup.security.UserInformation;
import com.codeup.security.Users;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class SignUpController {
    private Users users;
    private PasswordEncoder encoder;
    private Publisher publisher;

    public SignUpController(Users users, PasswordEncoder encoder, Publisher publisher) {
        this.users = users;
        this.encoder = encoder;
        this.publisher = publisher;
    }

    @GetMapping("/sign-up")
    public String showRegisterForm(Model viewModel) {
        viewModel.addAttribute("userInformation", new UserInformation());
        return "users/sign-up";
    }

    @PostMapping("/sign-up")
    public String registerUser(@Valid UserInformation userInformation, BindingResult validation) {
        if (validation.hasErrors()) return "users/sign-up";

        User user = User.signUp(userInformation, encoder);
        users.save(user);
        publisher.publish(user.events());

        return "redirect:/login";
    }
}
