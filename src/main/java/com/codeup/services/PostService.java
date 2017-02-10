/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.services;

import com.codeup.models.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("postService")
public class PostService {
    private List<Post> posts = new ArrayList<>();

    public PostService() {
        createPosts();
    }

    private void createPosts() {
        for (int i = 0; i < 100; i++) {
            posts.add(
                new Post(i + 1, "Title " + (i + 1), "Body" + (i + 1))
            );
        }
    }

    public Post findOnePost(long id) {
        return posts.get((int) (id - 1));
    }

    public List<Post> findAllPosts() {
        return posts;
    }
}
