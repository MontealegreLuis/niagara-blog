/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.controllers;

import com.codeup.models.Ad;
import com.codeup.models.User;
import com.codeup.services.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AdsController {
    private AdService service;

    // how do i build the AdsController?
    // resolution
    // -> I need to build the ads service
    //    -> I need to build the ads repository

    // execution
    // repo = new AdsRepository();
    // service = new AdService(repo)
    // controller = new AdsController(service);


    @Autowired
    public AdsController(AdService service) {
        this.service = service;
    }

    @GetMapping("/ads")
    public String showAllAds(Model viewModel) {
        viewModel.addAttribute("ads", service.all());

        return "ads/index";
    }

    @GetMapping("/ads/{id}")
    public String showOneAd(@PathVariable int id, Model viewModel) {
        viewModel.addAttribute("ad", service.findOneAd(id));

        return "ads/show";
    }

    @GetMapping("/ads/create")
    public String showCreateAdForm(@ModelAttribute Ad ad, Model viewModel) {
        viewModel.addAttribute("ad", ad);
        return "ads/create";
    }

    @PostMapping("/ads/create")
    public String saveAd(
        @Valid Ad ad, // it calls @ModelAttribute first
        Errors validation,
        Model viewModel
    ) {
        if (validation.hasErrors()) {
            viewModel.addAttribute("errors", validation);
            viewModel.addAttribute("ad", ad);
            return "ads/create";
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ad.setUser(user);
        service.save(ad);

        return "redirect:/ads";
    }

    @GetMapping("/ads/{id}/edit")
    public String showEditAdForm(@PathVariable int id, Model viewModel) {
        viewModel.addAttribute("ad", service.findOneAd(id));
        return "ads/edit";
    }

    @PostMapping("/ads/{id}/edit")
    public String updateAd(@ModelAttribute Ad ad, Model viewModel) {
        service.update(ad);
        viewModel.addAttribute("ad", ad);
        return "redirect:/ads";
    }
}
