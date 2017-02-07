/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MathController {
    @GetMapping("/add/{number1}/and/{number2}")
    @ResponseBody
    public String add(@PathVariable double number1, @PathVariable double number2) {
        return "<h1>" + number1 + " + " + number2  + " = " +
            (number1 + number2) + "</h1>";
    }

    @GetMapping("/subtract/{number1}/from/{number2}")
    @ResponseBody
    public String subtract(@PathVariable int number1, @PathVariable int number2) {
        return "<h1>" + number1 + " - " + number2  + " = " +
            (number1 - number2) + "</h1>";
    }

    @GetMapping("/multiply/{number1}/and/{number2}")
    @ResponseBody
    public String multiply(@PathVariable int number1, @PathVariable int number2) {
        return "<h1>" + number1 + " * " + number2  + " = " +
            (number1 * number2) + "</h1>";
    }

    @GetMapping("/divide/{number1}/by/{number2}")
    @ResponseBody
    public String divide(@PathVariable int number1, @PathVariable int number2) {
        return "<h1>" + number1 + " / " + number2  + " = " +
            (number1 / number2) + "</h1>";
    }
}
