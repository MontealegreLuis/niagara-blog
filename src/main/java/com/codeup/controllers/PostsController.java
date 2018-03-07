package com.codeup.controllers;

import com.codeup.blog.Post;
import com.codeup.blog.User;
import com.codeup.services.PostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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

    @GetMapping("/posts/{id}")
    public String viewSinglePost(@PathVariable long id, Model viewModel) {
        viewModel.addAttribute("post", service.findOnePost(id));

        return "posts/show";
    }

    @GetMapping("/posts/{id}/edit")
    @PreAuthorize("@postOwnerExpression.isAuthor(principal, #id)")
    public String showEditPostForm(@PathVariable Long id, Model viewModel) {
        viewModel.addAttribute("id", id);
        viewModel.addAttribute("postInformation", service.findOnePost(id));
        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    @PreAuthorize("@postOwnerExpression.isAuthor(principal, #post.id)")
    public String updatePost(@Valid Post post) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setAuthor(user);
        service.update(post);
        return "redirect:/posts";
    }

    @PostMapping("/posts/{id}/delete")
    @PreAuthorize("@postOwnerExpression.isAuthor(principal, #id)")
    public String deletePost(@PathVariable Long id) {
        service.deletePostWith(id);
        return "redirect:/posts";
    }
}
