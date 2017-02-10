/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.repositories;

import com.codeup.models.Ad;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// insert -> name, values, columns
// select -> table, columns, values
// update -> table, columns, values
// delete -> table, values

@Repository
public interface AdsRepository extends CrudRepository<Ad, Integer> {
}
