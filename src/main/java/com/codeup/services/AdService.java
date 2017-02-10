/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.services;

import com.codeup.models.Ad;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdService {
    private List<Ad> ads = new ArrayList<>();

    public AdService() {
        createAds();
    }

    private void createAds() {
        for (int i = 0; i < 100; i++) {
            save(new Ad("Title " + (i + 1), "Description " + (i + 1)));
        }
    }

    public void save(Ad ad) {
        ad.setId(ads.size() + 1);
        ads.add(ad);
    }

    public List<Ad> all() {
        return ads;
    }

    public Ad findOneAd(int id) {
        return ads.get(id - 1);
    }

    public void update(Ad ad) {
        ads.set(ad.getId() - 1, ad);
    }
}
