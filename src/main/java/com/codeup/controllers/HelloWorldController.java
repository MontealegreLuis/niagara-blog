/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloWorldController {

    @GetMapping("/home")
    public String homePage() {
        return "home";  // home.html
    }

    @GetMapping("/contact")
    public String contactPage() {
        return "contact/form"; // contact/form.html
    }

    @GetMapping("/hello/{name}")
    @ResponseBody
    public String hello(@PathVariable String name) {
        return formatGreeting(name);
    }

    private String formatGreeting(String name) {
        return "<h1>Hello " + name + " from Spring!!!!</h1>";
    }

    @RequestMapping(
        path = "/bye/{name}", method = {RequestMethod.GET, RequestMethod.POST}
    )
    @ResponseBody
    public String bye(@PathVariable String name) {
        return "<h1>Goodbye " + name + "!  from Spring</h1>";
    }
}
