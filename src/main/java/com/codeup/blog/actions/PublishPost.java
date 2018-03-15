/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.blog.actions;

import com.codeup.blog.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PublishPost {
    private Posts posts;
    private ImageUploader uploader;

    public PublishPost(Posts posts, ImageUploader uploader) {
        this.posts = posts;
        this.uploader = uploader;
    }

    @CacheEvict(value = "all-posts", allEntries = true)
    @CachePut(value = "single-post", key = "#result.id")
    public Post publish(
        PostInformation information,
        User author,
        MultipartFile image
    ) throws IOException {
        Post post = Post.publish(information, author);

        if (!image.isEmpty()) post.setImage(uploader.upload(image));

        return posts.save(post);
    }
}
