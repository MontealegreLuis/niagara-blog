/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.blog;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface Posts extends CrudRepository<Post, Long> {
    @Query("from Post p join p.author where p.id = ?1")
    Post findById(Long id);
}
