/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.blog.actions;

import com.codeup.blog.Post;
import com.codeup.blog.Posts;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ViewAllPosts {
    private Posts repository;

    public ViewAllPosts(Posts repository) {
        this.repository = repository;
    }

    @Cacheable(value = "all-posts")
    public Iterable<Post> viewAllPosts() {
        return repository.findAll();
    }
}
