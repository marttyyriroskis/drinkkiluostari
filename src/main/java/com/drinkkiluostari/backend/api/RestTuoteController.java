package com.drinkkiluostari.backend.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import com.drinkkiluostari.backend.domain.Tuote;
import com.drinkkiluostari.backend.repository.KategoriaRepository;
import com.drinkkiluostari.backend.repository.TuoteRepository;

@RestController
@RequestMapping("/api/tuotteet")
public class RestTuoteController {
    @Autowired
    TuoteRepository tuoteRepository;

    @Autowired
    KategoriaRepository kategoriaRepository;

    // Get tuotteet
    @GetMapping("")
    public Iterable<Tuote> getTuotteet() {
        return tuoteRepository.findAll();
    }
    
    // Get tuote by id
    @GetMapping("{id}")
    public Optional<Tuote> getTuote(@PathVariable("id") Long tuoteId) {
        return tuoteRepository.findById(tuoteId);
    }
    
    // Post a new tuote
    @PostMapping("")
    public Tuote newTuote(@RequestBody Tuote newTuote) {
        return tuoteRepository.save(newTuote);
    }
    
    // Edit tuote
    @PutMapping("{id}")
    public Tuote editTuote(@RequestBody Tuote editedTuote, @PathVariable Long id) {
        editedTuote.setId(id);
        return tuoteRepository.save(editedTuote);
    }

    // Delete tuote
    @DeleteMapping("{id}")
    public Iterable<Tuote> deleteTuote(@PathVariable Long id) {
        tuoteRepository.deleteById(id);
        return tuoteRepository.findAll();
    }

}
