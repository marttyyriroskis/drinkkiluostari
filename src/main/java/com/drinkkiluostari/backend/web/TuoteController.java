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
    @GetMapping("tuoteList")
    public String getTuotteet(Model model) {
        model.addAttribute("tuotteet", tuoteRepository.findAll());
        return "tuoteList";
    }

    // Add new tuote
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("tuoteNew")
    public String newTuote(Model model) {
        model.addAttribute("tuote", new Tuote());
        model.addAttribute("kategoriat", kategoriaRepository.findAll());
        model.addAttribute("tilausrivit", tilausriviRepository.findAll());
        return "tuoteNew";
    }

    // Save a new tuote
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("tuoteSave")
    public String saveTuote(@Valid @ModelAttribute("tuote") Tuote tuote, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tuoteEdit", tuote);
            model.addAttribute("kategoriat", kategoriaRepository.findAll());
            model.addAttribute("tilausrivit", tilausriviRepository.findAll());
            return "tuoteNew";
        }
        tuoteRepository.save(tuote);
        return "redirect:tuoteList";
    }

    // Edit tuote
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("tuoteEdit/{id}")
    public String editTuote(@PathVariable("id") Long id, Model model) {
        model.addAttribute("tuoteEdit", tuoteRepository.findById(id));
        model.addAttribute("kategoriat", kategoriaRepository.findAll());
        model.addAttribute("tilausrivit", tilausriviRepository.findAll());
        return "tuoteEdit";
    }

    // Save an edited tuote
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("tuoteSaveEdited")
    public String saveEditedTuote(@Valid @ModelAttribute("tuote") Tuote tuote, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tuoteEdit", tuote);
            model.addAttribute("kategoriat", kategoriaRepository.findAll());
            model.addAttribute("tilausrivit", tilausriviRepository.findAll());
            return "tuoteEdit";
        }
        tuoteRepository.save(tuote);
        return "redirect:tuoteList";
    }

    // Delete tuote
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("tuoteDelete/{id}")
    public String deleteTuote(@PathVariable("id") Long id, Model model) {
        tuoteRepository.deleteById(id);
        return "redirect:tuoteList";
    }

}
