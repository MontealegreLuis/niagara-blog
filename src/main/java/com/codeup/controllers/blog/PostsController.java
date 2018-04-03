package com.codeup.controllers.blog;

import com.codeup.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostsController {
    private PostService service;

    public PostsController(PostService service) {
        this.service = service;
    }

    @GetMapping({"/", "/posts"})
    public String viewAllPosts(Model viewModel){
        viewModel.addAttribute("posts", service.findAllPosts());

        return "posts/index";
    }
}
