/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.blog.actions;

import com.codeup.blog.Post;
import com.codeup.blog.PostInformation;
import com.codeup.blog.Posts;
import com.codeup.blog.UnknownPost;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class UpdatePost {
    private Posts posts;

    public UpdatePost(Posts posts) {
        this.posts = posts;
    }

    @CacheEvict(value = "all-posts", allEntries = true)
    @CachePut(value = "single-post", key = "#id")
    public void update(Long id, PostInformation postInformation) {
        Post post = posts.findById(id);

        if (post == null) throw UnknownPost.with(id);

        post.update(postInformation);
        posts.save(post);
    }
}
