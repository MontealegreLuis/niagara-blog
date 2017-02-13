/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.repositories;

import com.codeup.models.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepository extends CrudRepository<UserRole, Long> {

    // SQL -> Structured Query Language -> tables
    // HQL -> Hibernate Query Language -> objects -> entities

    @Query("select ur.role from UserRole ur, User u where u.username=?1 and ur.userId = u.id")
    public List<String> ofUserWith(String username);
}




