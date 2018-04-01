/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.blog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "posts")
public class Post implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 5000, nullable = false)
    private String body;

    private String image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    private Post(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public static Post publish(PostInformation information, User author) {
        Post post = new Post(information.getTitle(), information.getBody());
        post.setAuthor(author);

        return post;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public User getAuthor() {
        return author;
    }

    public long getId() {
        return id;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isAuthoredBy(User user) {
        return author.equals(user);
    }

    public void update(PostInformation information) {
        title = information.getTitle();
        body = information.getBody();
    }
}
