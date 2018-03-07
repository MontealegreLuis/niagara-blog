/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.controllers.blog;

import com.codeup.blog.ImageUploader;
import com.codeup.blog.Post;
import com.codeup.blog.PostInformation;
import com.codeup.blog.User;
import com.codeup.services.PostService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class PublishPostController {
    private PostService service;
    private ImageUploader uploader;

    public PublishPostController(PostService service, ImageUploader uploader) {
        this.service = service;
        this.uploader = uploader;
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
        @RequestParam(name = "image_file") MultipartFile uploadedImage
    ) throws IOException {
        if (validation.hasErrors()) return "posts/create";

        User author = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = Post.publish(information, author);

        if (!uploadedImage.isEmpty()) post.setImage(uploader.upload(uploadedImage));

        service.save(post);

        return "redirect:/posts";
    }
}
