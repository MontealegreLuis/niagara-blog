/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.controllers;

import com.codeup.models.Post;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void it_finds_an_existing_vehicle() throws Exception {
        String title = "Testing in Spring Boot";
        String body = "Your first controller test";
        int id = 1;

        given(service.findOnePost(id)).willReturn(Post.publish(title, body));

        mvc.perform(get("/posts/" + id))
            .andExpect(status().isOk())
            .andExpect(content().string(Matchers.containsString(title)))
            .andExpect(content().string(Matchers.containsString(body)))
        ;
    }

    @Test
    public void it_returns_a_404_status_code_if_the_post_cannot_be_found() throws Exception {
        long id = -1;
        given(service.findOnePost(id)).willReturn(null);

        mvc.perform(get("/posts/" + id))
            .andExpect(status().isNotFound())
        ;
    }

}
