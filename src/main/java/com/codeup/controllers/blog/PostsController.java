package com.codeup.controllers.blog;

import com.codeup.services.PostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping("/posts/{id}/delete")
    @PreAuthorize("@postOwnerExpression.isAuthor(principal, #id)")
    public String deletePost(@PathVariable Long id) {
        service.deletePostWith(id);
        return "redirect:/posts";
    }
}
