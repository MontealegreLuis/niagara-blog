/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.blog;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnknownPost extends RuntimeException {
    private UnknownPost(String message) {
        super(message);
    }

    public static UnknownPost with(long id) {
        return new UnknownPost(String.format("Post with ID %d cannot be found", id));
    }
}
