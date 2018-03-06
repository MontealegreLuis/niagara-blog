package com.codeup.controllers;

import com.codeup.models.User;
import com.codeup.repositories.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class SignUpController {
    private UsersRepository users;
    private PasswordEncoder encoder;

    public SignUpController(UsersRepository users, PasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @GetMapping("/sign-up")
    public String showRegisterForm(Model viewModel) {
        viewModel.addAttribute("user", new User());
        return "users/sign-up";
    }

    @PostMapping("/sign-up")
    public String registerUser(
        @Valid User user,
        Errors validation,
        Model viewModel,
        @RequestParam(name = "password_confirm") String passwordConfirmation
    ) {
        if (!passwordConfirmation.equals(user.getPassword())) {
            validation.rejectValue(
                "password",
                "user.password",
                "Your passwords do not match"
            );
        }
        if (validation.hasErrors()) {
            viewModel.addAttribute("errors", validation);
            viewModel.addAttribute("user", user);
            return "users/sign-up";
        }

        user.encodePassword(encoder);
        users.save(user);

        return "redirect:/login";
    }
}
