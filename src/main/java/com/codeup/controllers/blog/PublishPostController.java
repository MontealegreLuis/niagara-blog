/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.controllers.blog;

import com.codeup.blog.Post;
import com.codeup.blog.PostInformation;
import com.codeup.blog.User;
import com.codeup.blog.actions.PublishPost;
import org.springframework.security.core.Authentication;
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
    private PublishPost action;

    public PublishPostController(PublishPost action) {
        this.action = action;
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
        @RequestParam(name = "image_file") MultipartFile uploadedImage,
        Authentication token
    ) throws IOException {
        if (validation.hasErrors()) return "posts/create";

        User author = (User) token.getPrincipal();
        Post post = action.publish(information, author, uploadedImage);

        return String.format("redirect:/posts/%d", post.getId());
    }
}
