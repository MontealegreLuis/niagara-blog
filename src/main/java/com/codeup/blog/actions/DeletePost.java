/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.blog.actions;

import com.codeup.blog.Post;
import com.codeup.blog.Posts;
import com.codeup.blog.UnknownPost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DeletePost {
    @Value("${blog.uploads.folder}")
    private String imagesFolder;

    private Posts posts;

    public DeletePost(Posts posts) {
        this.posts = posts;
    }

    @Caching(evict = {
        @CacheEvict(value = "single-post", key = "#id"),
        @CacheEvict(value = "all-posts", allEntries = true)
    })
    public void deletePostWithId(Long id) {
        Post post = posts.findById(id);

        if (post == null) throw UnknownPost.with(id);

        if (post.hasImage()) {
            File file = new File(String.format("%s/%s", imagesFolder, post.getImage()));
            file.delete();
        }

        posts.delete(id);
    }
}
