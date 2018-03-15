/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.controllers;

import com.codeup.blog.Post;
import com.codeup.blog.PostInformation;
import com.codeup.blog.UnknownPost;
import com.codeup.blog.User;
import com.codeup.services.PostService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class PostsControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService service;

    @Test
    public void it_shows_all_the_published_posts() throws Exception {
        User author = new User();
        given(service.findAllPosts())
            .willReturn(Arrays.asList(
                Post.publish(new PostInformation("Intro to Spring Boot", "Intro text for Spring Boot"), author),
                Post.publish(new PostInformation("Intro to JUnit", "Intro text for JUnit"), author),
                Post.publish(new PostInformation("Intro to Mockito", "Intro text for Mockito"), author)
            ))
        ;

        mvc.perform(get("/posts"))
            .andExpect(status().isOk())
            .andExpect(content().string(Matchers.containsString("Intro to Spring Boot")))
            .andExpect(content().string(Matchers.containsString("Intro to JUnit")))
            .andExpect(content().string(Matchers.containsString("Intro to Mockito")))
        ;
    }

    @Test
    public void it_finds_an_existing_vehicle() throws Exception {
        String title = "Testing in Spring Boot";
        String body = "Your first controller test";
        User author = new User();
        int id = 1;

        given(service.findOnePost(id)).willReturn(Post.publish(new PostInformation(title, body), author));

        mvc.perform(get("/posts/" + id))
            .andExpect(status().isOk())
            .andExpect(content().string(Matchers.containsString(title)))
            .andExpect(content().string(Matchers.containsString(body)))
        ;
    }

    @Test
    public void it_returns_a_404_status_code_if_the_post_cannot_be_found() throws Exception {
        long id = 0;
        given(service.findOnePost(id)).willThrow(UnknownPost.class);

        mvc.perform(get("/posts/" + id))
            .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void it_redirects_anonymous_user_to_login_page_when_trying_to_edit_a_post() throws Exception {
        int anyId = -1;
        mvc.perform(get("/posts/" + anyId + "/edit"))
            .andExpect(status().is(302))
            .andExpect(redirectedUrlPattern("**/login"))
        ;
    }
}
