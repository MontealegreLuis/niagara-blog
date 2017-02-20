/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.controllers;

import com.codeup.models.Ad;
import com.codeup.models.User;
import com.codeup.services.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Controller
public class AdsController {
    @Value("${uploads}")
    private String uploadsPath;

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
        viewModel.addAttribute("ads", Collections.emptyList());

        return "ads/index";
    }

    @GetMapping("/ads.json")
    public @ResponseBody List<Ad> retrieveAllAds() {
        return service.all();
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
        Model viewModel,
        @RequestParam(name = "image_file") MultipartFile uploadedFile
    ) throws IOException {
        if (validation.hasErrors()) {
            viewModel.addAttribute("errors", validation);
            viewModel.addAttribute("ad", ad);
            return "ads/create";
        }
        // unix based : mac, linux -> the folder for temporary files is always
        // /tmp -> kljsah12312 (temporary random name)
        String filename = uploadedFile.getOriginalFilename();
        String destinationPath = Paths.get(uploadsPath, filename).toString();
        uploadedFile.transferTo(new File(destinationPath)); // it will move the file in this step

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ad.setUser(user);
        ad.setImage(filename);
        service.save(ad);

        return "redirect:/ads";
    }

    @GetMapping("/ads/{id}/edit")
    @PreAuthorize("@adOwnerExpression.isOwner(principal, #id)")
    public String showEditAdForm(@PathVariable int id, Model viewModel) {
        viewModel.addAttribute("ad", service.findOneAd(id));
        return "ads/edit";
    }

    @PostMapping("/ads/{id}/edit")
    @PreAuthorize("@adOwnerExpression.isOwner(principal, #ad.id)")
    public String updateAd(@ModelAttribute Ad ad, Model viewModel) {
        service.update(ad);
        viewModel.addAttribute("ad", ad);
        return "redirect:/ads";
    }
}
