/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.controllers;

import com.codeup.models.User;
import com.codeup.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class AuthenticationController {
    private UsersRepository repository;
    private PasswordEncoder encoder;

    @Autowired
    public AuthenticationController(UsersRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model viewModel) {
        viewModel.addAttribute("user", new User());
        return "users/register";
    }

    @PostMapping("/users/create")
    public String registerUser(
        @Valid User user, // create the user from the input values, and apply validations
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
            return "users/register";
        }
        String hashedPassword = encoder.encode(user.getPassword()); // hash the user's password
        user.setPassword(hashedPassword);
        repository.save(user); // save the user to the database
        return "redirect:/login"; // redirect the user to the login page
    }
}
