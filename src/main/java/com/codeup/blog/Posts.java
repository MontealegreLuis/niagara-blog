/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.blog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Posts extends JpaRepository<Post, Long> {
    @Query("from Post p join p.author where p.id = ?1")
    Post findById(Long id);
}
