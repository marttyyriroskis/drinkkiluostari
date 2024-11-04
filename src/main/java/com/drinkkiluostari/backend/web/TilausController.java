package com.drinkkiluostari.backend.web;

import java.util.Comparator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import com.drinkkiluostari.backend.domain.Tilaus;
import com.drinkkiluostari.backend.repository.AsiakasRepository;
import com.drinkkiluostari.backend.repository.TilausRepository;
import com.drinkkiluostari.backend.repository.TilausriviRepository;
import com.drinkkiluostari.backend.repository.TyontekijaRepository;

import jakarta.validation.Valid;

@Controller
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
        List<Tilaus> tilaukset = tilausRepository.findAllActive();
        tilaukset.sort(Comparator.comparing(Tilaus::getId));
        model.addAttribute("tilaukset", tilaukset);
        return "tilausList";
    }

    // Add new tilaus
    @GetMapping("/tilausNew")
    public String newTilaus(Model model) {
        model.addAttribute("tilaus", new Tilaus());
        model.addAttribute("asiakkaat", asiakasRepository.findAllActive());
        model.addAttribute("tyontekijat", tyontekijaRepository.findAllActive());
        model.addAttribute("tilausrivit", tilausriviRepository.findAll());
        return "tilausNew";
    }

    // Save a new tilaus
    @PostMapping("/tilausSave")
    public String saveTilaus(@Valid Tilaus tilaus, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tilausEdit", tilaus);
            model.addAttribute("asiakkaat", asiakasRepository.findAllActive());
            model.addAttribute("tyontekijat", tyontekijaRepository.findAllActive());
            model.addAttribute("tilausrivit", tilausriviRepository.findAll());
            return "tilausNew";
        }

        tilaus.create();

        tilausRepository.save(tilaus);
        return "redirect:tilausList";
    }

    // Edit tilaus
    @GetMapping("/tilausEdit/{id}")
    public String editTilaus(@PathVariable Long id, Model model) {
        model.addAttribute("tilausEdit", tilausRepository.findByIdActive(id));
        model.addAttribute("asiakkaat", asiakasRepository.findAllActive());
        model.addAttribute("tyontekijat", tyontekijaRepository.findAllActive());
        model.addAttribute("tilausrivit", tilausriviRepository.findAll());
        return "tilausEdit";
    }

    // Save an edited tilaus
    @PostMapping("/tilausSaveEdited")
    public String saveEditedTilaus(@Valid @ModelAttribute("tilausEdit") Tilaus tilaus, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tilausEdit", tilaus);
            model.addAttribute("asiakkaat", asiakasRepository.findAllActive());
            model.addAttribute("tyontekijat", tyontekijaRepository.findAllActive());
            model.addAttribute("tilausrivit", tilausriviRepository.findAll());
            return "tilausEdit";
        }
        
        if (tilaus.getAsiakas() != null && tilaus.getAsiakas().getId() == null) {
            asiakasRepository.save(tilaus.getAsiakas());
        }

        tilaus.edit();

        tilausRepository.save(tilaus);
        return "redirect:tilausList";
    }

    // Delete tilaus
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/tilausDelete/{id}")
    public String deleteTilaus(@PathVariable Long id, Model model) {
        Tilaus tilaus = tilausRepository.findByIdActive(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tilaus not found"));
        
        tilaus.delete();

        tilausRepository.save(tilaus);
        return "redirect:tilausList";
    }

}
