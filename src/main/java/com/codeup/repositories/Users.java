/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.repositories;

import com.codeup.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Users extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
