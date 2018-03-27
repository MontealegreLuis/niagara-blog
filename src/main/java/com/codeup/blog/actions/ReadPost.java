/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.blog.actions;

import com.codeup.blog.Post;
import com.codeup.blog.Posts;
import com.codeup.blog.UnknownPost;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ReadPost {
    private Posts posts;

    public ReadPost(Posts posts) {
        this.posts = posts;
    }

    @Cacheable(value = "single-post", key = "#id")
    public Post readPostWith(Long id) {
        Post post = posts.findById(id);

        if (post == null) throw UnknownPost.with(id);

        return post;
    }
}
