/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RollDiceController {

    @GetMapping("/roll-dice")
    public String showLinksWithNumbers(Model viewModel) {   // View model

        viewModel.addAttribute("numbers", new int[]{1, 2, 3, 4, 5, 6});

        return "roll-dice/roll-dice-links";
    }

    @GetMapping("/roll-dice/{n}")
    public String compareGuess(@PathVariable int n, Model viewModel) {
        // Generate a random number between 1 and 6
        int rollDice = (int) (Math.random() * 6 + 1);

        viewModel.addAttribute("n", n);
        viewModel.addAttribute("rollDice", rollDice);

        return "roll-dice/roll-dice-result";
    }
}
