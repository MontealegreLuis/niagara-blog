package com.codeup.controllers;

import com.codeup.blog.Post;
import com.codeup.blog.PostInformation;
import com.codeup.blog.User;
import com.codeup.services.PostService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Controller
public class PublishPostController {
    @Value("${uploads.folder}")
    private String uploadsFolder;

    private PostService service;

    public PublishPostController(PostService service) {  // local variable
        this.service = service;
    }

    @GetMapping("/posts/create")
    public String viewCreatePostForm(Model viewModel) {
        viewModel.addAttribute("postInformation", new PostInformation());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(
        @Valid PostInformation information,
        BindingResult validation,
        @RequestParam(name = "image_file") MultipartFile uploadedFile
    ) throws IOException {
        if (validation.hasErrors()) return "posts/create";

        User author = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = Post.publish(information, author);

        if (!uploadedFile.isEmpty()) {
            String filename = uploadedFile.getOriginalFilename();
            String destinationPath = Paths.get(uploadsFolder(), filename).toString();
            uploadedFile.transferTo(new File(destinationPath));
            post.setImage(filename);
        }

        service.save(post);

        return "redirect:/posts";
    }

    private String uploadsFolder() throws IOException {
        return String.format("%s/%s", new File(".").getCanonicalPath(), uploadsFolder);
    }
}
