package com.codeup.controllers.blog;

import com.codeup.blog.actions.ViewAllPosts;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewAllPostsController {
    private ViewAllPosts action;

    public ViewAllPostsController(ViewAllPosts action) {
        this.action = action;
    }

    @GetMapping({"/", "/posts"})
    public String viewAllPosts(Model viewModel){
        viewModel.addAttribute("posts", action.viewAllPosts());

        return "posts/index";
    }
}
