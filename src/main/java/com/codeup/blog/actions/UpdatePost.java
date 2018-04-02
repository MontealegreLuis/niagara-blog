/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.blog.actions;

import com.codeup.blog.*;
import com.codeup.infrastructure.events.Publisher;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UpdatePost {
    private Posts posts;
    private ImageUploader uploader;
    private Publisher publisher;

    public UpdatePost(Posts posts, ImageUploader uploader, Publisher publisher) {
        this.posts = posts;
        this.uploader = uploader;
        this.publisher = publisher;
    }

    @CacheEvict(value = "all-posts", allEntries = true)
    @CachePut(value = "single-post", key = "#id")
    public void update(
        Long id,
        PostInformation postInformation,
        MultipartFile image
    ) throws IOException {
        Post post = posts.findById(id);

        if (post == null) throw UnknownPost.with(id);

        if (!image.isEmpty()) post.setImage(uploader.upload(image));

        post.update(postInformation);
        publisher.publish(post.events());
        posts.save(post);
    }
}
