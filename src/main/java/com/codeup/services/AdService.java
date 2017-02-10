/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.services;

import com.codeup.models.Ad;
import com.codeup.repositories.AdsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdService {
    private AdsRepository repository;

    @Autowired
    public AdService(AdsRepository repository) {
        this.repository = repository;
    }

    public void save(Ad ad) {
        repository.save(ad); // insert into ads (title, description) values (?, ?)
    }

    public List<Ad> all() {
        // Iterable -> List  (casting it)
        return (List<Ad>) repository.findAll(); // select * from ads
    }

    public Ad findOneAd(int id) {
        return repository.findOne(id); // select * from ads where id = ?
    }

    public void update(Ad ad) {
        repository.save(ad); // update
    }
}
