/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.security;

import com.codeup.models.Ad;
import com.codeup.models.User;
import com.codeup.repositories.AdsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdOwnerExpression {
    private AdsRepository ads;

    @Autowired
    public AdOwnerExpression(AdsRepository ads) {
        this.ads = ads;
    }

    public boolean isOwner(User currentUser, int adId) {
        Ad ad = ads.findOne(adId);
        User owner = ad.getUser();
        return owner != null && owner.getId() ==  currentUser.getId();
    }
}
