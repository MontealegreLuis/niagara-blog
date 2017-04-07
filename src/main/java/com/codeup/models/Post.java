/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.models;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @Column(length = 5000, nullable = false)
    @NotBlank(message = "Posts cannot be empty")
    @Size(min = 5, message = "Posts must have at least 5 characters")
    private String body;

    public Post() {
    }

    private Post(String title, String body) {
        setTitle(title);
        setBody(body);
    }

    public static Post publish(String title, String body) {
        return new Post(title, body);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        Assert.notNull(title, "Title cannot be empty");
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        Assert.notNull(body, "Posts cannot be empty");
        this.body = body;
    }
}
