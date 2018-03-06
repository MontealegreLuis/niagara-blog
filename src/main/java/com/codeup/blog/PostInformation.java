package com.codeup.blog;

import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Setter
public class PostInformation {
    @NotNull(message = "All posts must have a title")
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotNull(message = "All posts must have a body")
    @NotBlank(message = "Posts cannot be empty")
    private String body;

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
