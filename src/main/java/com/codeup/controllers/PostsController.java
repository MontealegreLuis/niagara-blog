package com.codeup.controllers;

import com.codeup.models.Post;
import com.codeup.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class PostsController {
    private PostService service;

    @Autowired
    public PostsController(PostService service) {  // local variable
        this.service = service;
    }

    @GetMapping("/posts")
    public String viewAllPosts(Model viewModel){
        Iterable<Post> posts = service.findAllPosts();
        viewModel.addAttribute("posts", posts);

        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String viewSinglePost(@PathVariable long id, Model viewModel) {
        Post post = service.findOnePost(id);
        viewModel.addAttribute("post", post);

        return "posts/show";
    }

    @GetMapping("/posts/create")
    public String viewCreatePostForm(Model viewModel) {
        viewModel.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(
        @Valid Post post,
        Errors validation,
        Model viewModel
    ) {
        if (validation.hasErrors()) {
            viewModel.addAttribute("errors", validation);
            viewModel.addAttribute("post", post);
            return "ads/create";
        }

        service.save(post);

        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String showEditPostForm(@PathVariable Long id, Model viewModel) {
        viewModel.addAttribute("post", service.findOnePost(id));
        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String updatePost(@Valid Post post, Model viewModel) {
        service.update(post);
        viewModel.addAttribute("post", post);
        return "redirect:/posts";
    }
}
