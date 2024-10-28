package com.drinkkiluostari.backend.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import com.drinkkiluostari.backend.repository.TuoteRepository;
import com.drinkkiluostari.backend.domain.Tuote;
import com.drinkkiluostari.backend.repository.KategoriaRepository;
import com.drinkkiluostari.backend.repository.TilausriviRepository;

import jakarta.validation.Valid;

@Controller
@Validated
public class TuoteController {
    private final TuoteRepository tuoteRepository;
    private final KategoriaRepository kategoriaRepository;
    private final TilausriviRepository tilausriviRepository;
    
    public TuoteController(TuoteRepository tuoteRepository, KategoriaRepository kategoriaRepository, TilausriviRepository tilausriviRepository) {
        this.tuoteRepository = tuoteRepository;
        this.kategoriaRepository = kategoriaRepository;
        this.tilausriviRepository = tilausriviRepository;
    }

    // Get tuotteet
    @GetMapping("/tuoteList")
    public String getTuotteet(Model model) {
        model.addAttribute("tuotteet", tuoteRepository.findAllActive());
        return "/tuoteList";
    }

    // Add new tuote
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/tuoteNew")
    public String newTuote(Model model) {
        model.addAttribute("tuote", new Tuote());
        model.addAttribute("kategoriat", kategoriaRepository.findAll());
        model.addAttribute("tilausrivit", tilausriviRepository.findAll());
        return "/tuoteNew";
    }

    // Save a new tuote
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/tuoteSave")
    public String saveTuote(@Valid @ModelAttribute("tuote") Tuote tuote, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tuoteEdit", tuote);
            model.addAttribute("kategoriat", kategoriaRepository.findAll());
            model.addAttribute("tilausrivit", tilausriviRepository.findAll());
            return "/tuoteNew";
        }
        tuoteRepository.save(tuote);
        return "redirect:/tuoteList";
    }

    // Edit tuote
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/tuoteEdit/{id}")
    public String editTuote(@PathVariable("id") Long id, Model model) {
        model.addAttribute("tuoteEdit", tuoteRepository.findByIdActive(id));
        model.addAttribute("kategoriat", kategoriaRepository.findAll());
        model.addAttribute("tilausrivit", tilausriviRepository.findAll());
        return "/tuoteEdit";
    }

    // Save an edited tuote
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/tuoteSaveEdited")
    public String saveEditedTuote(@Valid @ModelAttribute("tuote") Tuote tuote, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tuoteEdit", tuote);
            model.addAttribute("kategoriat", kategoriaRepository.findAll());
            model.addAttribute("tilausrivit", tilausriviRepository.findAll());
            return "/tuoteEdit";
        }

        if (tuote.getKategoria() != null && tuote.getKategoria().getId() == null) {
            kategoriaRepository.save(tuote.getKategoria());
        }

        tuoteRepository.save(tuote);
        return "redirect:/tuoteList";
    }

    // Delete tuote
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/tuoteDelete/{id}")
    public String deleteTuote(@PathVariable("id") Long id, Model model) {
        Tuote tuote = tuoteRepository.findByIdActive(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tuote not found"));
        
        tuote.delete();

        tuoteRepository.save(tuote);
        return "redirect:/tuoteList";
    }

}
