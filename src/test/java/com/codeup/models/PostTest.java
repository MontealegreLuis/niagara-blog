/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.models;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class PostTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void it_can_be_published()
    {
        String title = "New post";
        String body = "Some content";

        Post post = Post.publish(title, body);

        assertThat(post.getTitle(), equalTo(title));
        assertThat(post.getBody(), equalTo(body));
    }

    @Test
    public void it_cannot_be_published_without_a_title()
    {
        exception.expect(IllegalArgumentException.class);
        Post.publish(null, "Some content");
    }

    @Test
    public void it_cannot_be_published_without_a_body()
    {
        exception.expect(IllegalArgumentException.class);
        Post.publish("A title", null);
    }
}
