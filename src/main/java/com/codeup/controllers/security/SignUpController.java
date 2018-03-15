/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.controllers.security;

import com.codeup.blog.actions.SignUp;
import com.codeup.security.UserInformation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class SignUpController {
    private SignUp action;

    public SignUpController(SignUp action) {
        this.action = action;
    }

    @GetMapping("/sign-up")
    public String showRegisterForm(Model viewModel) {
        viewModel.addAttribute("userInformation", new UserInformation());
        return "users/sign-up";
    }

    @PostMapping("/sign-up")
    public String registerUser(@Valid UserInformation userInformation, BindingResult validation) {
        if (validation.hasErrors()) return "users/sign-up";

        action.signUpWith(userInformation);

        return "redirect:/login";
    }
}
