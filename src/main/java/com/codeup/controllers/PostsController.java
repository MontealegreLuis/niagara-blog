package com.codeup.controllers;

import com.codeup.exceptions.PostNotFound;
import com.codeup.models.Post;
import com.codeup.models.PostInformation;
import com.codeup.models.User;
import com.codeup.services.PostService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Controller
public class PostsController {

    @Value("${uploads.folder}")
    private String uploadsFolder;

    private PostService service;

    @Autowired
    public PostsController(PostService service) {  // local variable
        this.service = service;
    }

    @GetMapping({"/", "/posts"})
    public String viewAllPosts(Model viewModel){
        viewModel.addAttribute("posts", service.findAllPosts());

        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String viewSinglePost(@PathVariable long id, Model viewModel) {
        Post post = service.findOnePost(id);

        if (post == null) {
            throw new PostNotFound(String.format("Post with ID %d cannot be found", id));
        }

        viewModel.addAttribute("post", post);

        return "posts/show";
    }

    @GetMapping("/posts/create")
    public String viewCreatePostForm(Model viewModel) {
        viewModel.addAttribute("postInformation", new PostInformation());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(
        @Valid PostInformation information,
        Errors validation,
        Model viewModel,
        @RequestParam(name = "image_file") MultipartFile uploadedFile
    ) throws IOException {
        if (validation.hasErrors()) {
            viewModel.addAttribute("errors", validation);
            viewModel.addAttribute("postInformation", information);
            return "posts/create";
        }

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

    @GetMapping("/posts/image/{filename:.+}")
    public HttpEntity<byte[]> showPostsImage(@PathVariable String filename) throws IOException {
        String imagePath = String.format(
            "%s/%s",
            uploadsFolder(),
            filename
        );
        byte[] image = IOUtils.toByteArray(FileUtils.openInputStream(new File(imagePath)));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(image.length);

        return new HttpEntity<>(image, headers);
    }

    private String uploadsFolder() throws IOException {
        return String.format("%s/%s", new File(".").getCanonicalPath(), uploadsFolder);
    }
}
