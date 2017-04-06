/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.services;

import com.codeup.models.Post;
import com.codeup.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private PostsRepository posts;

    @Autowired
    public PostService(PostsRepository posts) {
        this.posts = posts;
    }

    public Post findOnePost(long id) {
        return posts.findOne(id);
    }

    public Iterable<Post> findAllPosts() {
        return posts.findAll();
    }
}
