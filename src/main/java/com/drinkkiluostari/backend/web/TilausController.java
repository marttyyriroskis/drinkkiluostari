package com.drinkkiluostari.backend.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.drinkkiluostari.backend.domain.Tilaus;
import com.drinkkiluostari.backend.repository.AsiakasRepository;
import com.drinkkiluostari.backend.repository.TilausRepository;
import com.drinkkiluostari.backend.repository.TilausriviRepository;
import com.drinkkiluostari.backend.repository.TyontekijaRepository;

import jakarta.validation.Valid;

@Controller
@Validated
public class TilausController {
    private final TilausRepository tilausRepository;
    private final AsiakasRepository asiakasRepository;
    private final TyontekijaRepository tyontekijaRepository;
    private final TilausriviRepository tilausriviRepository;
    
    public TilausController(TilausRepository tilausRepository, AsiakasRepository asiakasRepository, TyontekijaRepository tyontekijaRepository,
    TilausriviRepository tilausriviRepository) {
        this.tilausRepository = tilausRepository;
        this.asiakasRepository = asiakasRepository;
        this.tyontekijaRepository = tyontekijaRepository;
        this.tilausriviRepository = tilausriviRepository;
    }

    // Get tilaukset
    @GetMapping("/tilausList")
    public String getTilaukset(Model model) {
        model.addAttribute("tilaukset", tilausRepository.findAll());
        return "/tilausList";
    }

    // Add new tilaus
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/tilausNew")
    public String newTilaus(Model model) {
        model.addAttribute("tilaus", new Tilaus());
        model.addAttribute("asiakkaat", asiakasRepository.findAll());
        model.addAttribute("tyontekijat", tyontekijaRepository.findAll());
        model.addAttribute("tilausrivit", tilausriviRepository.findAll());
        return "/tilausNew";
    }

    // Save a new tilaus
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/tilausSave")
    public String saveTilaus(@Valid @ModelAttribute("tilaus") Tilaus tilaus, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tilausEdit", tilaus);
            model.addAttribute("asiakkaat", asiakasRepository.findAll());
            model.addAttribute("tyontekijat", tyontekijaRepository.findAll());
            model.addAttribute("tilausrivit", tilausriviRepository.findAll());
            return "/tilausNew";
        }
        tilausRepository.save(tilaus);
        return "redirect:/tilausList";
    }

    // Edit tilaus
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/tilausEdit/{id}")
    public String editTilaus(@PathVariable("id") Long id, Model model) {
        model.addAttribute("tilausEdit", tilausRepository.findById(id));
        model.addAttribute("asiakkaat", asiakasRepository.findAll());
        model.addAttribute("tyontekijat", tyontekijaRepository.findAll());
        model.addAttribute("tilausrivit", tilausriviRepository.findAll());
        return "/tilausEdit";
    }

    // Save an edited tilaus
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/tilausSaveEdited")
    public String saveEditedTilaus(@Valid @ModelAttribute("tilaus") Tilaus tilaus, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tilausEdit", tilaus);
            model.addAttribute("asiakkaat", asiakasRepository.findAll());
            model.addAttribute("tyontekijat", tyontekijaRepository.findAll());
            model.addAttribute("tilausrivit", tilausriviRepository.findAll());
            return "/tilausEdit";
        }
        
        if (tilaus.getAsiakas() != null && tilaus.getAsiakas().getId() == null) {
            asiakasRepository.save(tilaus.getAsiakas());
        }

        tilausRepository.save(tilaus);
        return "redirect:/tilausList";
    }

    // Delete tilaus
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/tilausDelete/{id}")
    public String deleteTilaus(@PathVariable("id") Long id, Model model) {
        tilausRepository.deleteById(id);
        return "redirect:/tilausList";
    }

}
