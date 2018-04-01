/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.services;

import com.codeup.blog.Post;
import com.codeup.blog.Posts;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private Posts repository;

    public PostService(Posts repository) {
        this.repository = repository;
    }

    @Cacheable(value = "all-posts")
    public Iterable<Post> findAllPosts() {
        return repository.findAll();
    }

    @Caching(evict = {
        @CacheEvict(value = "single-post", key = "#id"),
        @CacheEvict(value = "all-posts", allEntries = true)
    })
    public void deletePostWith(Long id) {
        repository.delete(id);
    }
}
