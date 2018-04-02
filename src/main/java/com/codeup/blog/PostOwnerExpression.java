/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.blog;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PostOwnerExpression {
    private Posts posts;

    public PostOwnerExpression(Posts posts) {
        this.posts = posts;
    }

    @Cacheable(value = "is-post-author", key = "#currentUser.id + '_' + #postId")
    public boolean isAuthor(User currentUser, Long postId) {
        Post post = posts.findOne(postId);
        return post.isAuthoredBy(currentUser);
    }
}
