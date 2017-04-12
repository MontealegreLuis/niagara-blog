/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFound extends RuntimeException {
    public PostNotFound(String message) {
        super(message);
    }
}
