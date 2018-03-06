/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.repositories;

import com.codeup.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface Posts extends CrudRepository<Post, Long> {
}
