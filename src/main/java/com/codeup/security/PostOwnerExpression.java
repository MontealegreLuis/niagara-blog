/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.security;

import com.codeup.models.Post;
import com.codeup.models.User;
import com.codeup.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostOwnerExpression {
    private PostsRepository posts;

    @Autowired
    public PostOwnerExpression(PostsRepository posts) {
        this.posts = posts;
    }

    public boolean isAuthor(User currentUser, Long postId) {
        Post post = posts.findOne(postId);
        User author = post.getAuthor();
        return author != null && author.getId() ==  currentUser.getId();
    }
}
