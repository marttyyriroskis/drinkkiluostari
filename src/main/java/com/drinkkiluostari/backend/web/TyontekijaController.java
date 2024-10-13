package com.drinkkiluostari.backend.web;

//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.drinkkiluostari.backend.domain.Tyontekija;
import com.drinkkiluostari.backend.repository.RooliRepository;
import com.drinkkiluostari.backend.repository.TilausRepository;
import com.drinkkiluostari.backend.repository.TyontekijaRepository;

import jakarta.validation.Valid;

@Controller
@Validated
public class TyontekijaController {
    private final TyontekijaRepository tyontekijaRepository;
    private final RooliRepository rooliRepository;
    private final TilausRepository tilausRepository;
    
    public TyontekijaController(TyontekijaRepository tyontekijaRepository, RooliRepository rooliRepository, TilausRepository tilausRepository) {
        this.tyontekijaRepository = tyontekijaRepository;
        this.rooliRepository = rooliRepository;
        this.tilausRepository = tilausRepository;
    }

    // Get työntekijät
    @GetMapping("tyontekijaList")
    public String getTyontekijat(Model model) {
        model.addAttribute("tyontekijat", tyontekijaRepository.findAll());
        return "tyontekijaList";
    }

    // Add new työntekijä
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("tyontekijaNew")
    public String newTyontekija(Model model) {
        model.addAttribute("tyontekija", new Tyontekija());
        model.addAttribute("roolit", rooliRepository.findAll());
        model.addAttribute("tilaukset", tilausRepository.findAll());
        return "tyontekijaNew";
    }

    // Save a new työntekijä
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("tyontekijaSave")
    public String saveTyontekija(@Valid @ModelAttribute("tyontekija") Tyontekija tyontekija, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tyontekijaEdit", tyontekija);
            model.addAttribute("roolit", rooliRepository.findAll());
            model.addAttribute("tilaukset", tilausRepository.findAll());
            return "tyontekijaNew";
        }
        tyontekijaRepository.save(tyontekija);
        return "redirect:tyontekijaList";
    }

    // Edit työntekijä
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("tyontekijaEdit/{id}")
    public String editTyontekija(@PathVariable("id") Long id, Model model) {
        model.addAttribute("tyontekijaEdit", tyontekijaRepository.findById(id));
        model.addAttribute("roolit", rooliRepository.findAll());
        model.addAttribute("tilaukset", tilausRepository.findAll());
        return "tyontekijaEdit";
    }

    // Save an edited työntekijä
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("tyontekijaSaveEdited")
    public String saveEditedTyontekija(@Valid @ModelAttribute("tyontekija") Tyontekija tyontekija, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tyontekijaEdit", tyontekija);
            model.addAttribute("roolit", rooliRepository.findAll());
            model.addAttribute("tilaukset", tilausRepository.findAll());
            return "tyontekijaEdit";
        }
        tyontekijaRepository.save(tyontekija);
        return "redirect:tyontekijaList";
    }

    // Delete työntekijä
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("tyontekijaDelete/{id}")
    public String deleteTyontekija(@PathVariable("id") Long id, Model model) {
        tyontekijaRepository.deleteById(id);
        return "redirect:tyontekijaList";
    }

}
