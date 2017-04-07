/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.services;

import com.codeup.models.Post;
import com.codeup.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private PostsRepository repository;

    @Autowired
    public PostService(PostsRepository repository) {
        this.repository = repository;
    }

    public Post findOnePost(long id) {
        return repository.findOne(id);
    }

    public Iterable<Post> findAllPosts() {
        return repository.findAll();
    }

    public void save(Post post) {
        repository.save(post);
    }
}
