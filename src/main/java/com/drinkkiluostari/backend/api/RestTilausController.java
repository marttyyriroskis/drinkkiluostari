package com.drinkkiluostari.backend.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drinkkiluostari.backend.domain.Tilaus;
import com.drinkkiluostari.backend.repository.AsiakasRepository;
import com.drinkkiluostari.backend.repository.TilausRepository;
import com.drinkkiluostari.backend.repository.TyontekijaRepository;

@RestController
@RequestMapping("/api/tilaukset")
public class RestTilausController {
    @Autowired
    TilausRepository tilausRepository;

    @Autowired
    AsiakasRepository asiakasRepository;

    @Autowired
    TyontekijaRepository tyontekijaRepository;

    // Get tilaukset
    @GetMapping("")
    public Iterable<Tilaus> getTilaukset() {
        return tilausRepository.findAll();
    }
    
    // Get tilaus by id
    @GetMapping("{id}")
    public Optional<Tilaus> getTilaus(@PathVariable("id") Long tilausId) {
        return tilausRepository.findById(tilausId);
    }
    
    // Post a new tilaus
    @PostMapping("")
    public Tilaus newTilaus(@RequestBody Tilaus newTilaus) {
        return tilausRepository.save(newTilaus);
    }
    
    // Edit tilaus
    @PutMapping("{id}")
    public Tilaus editTilaus(@RequestBody Tilaus editedTilaus, @PathVariable Long id) {
        editedTilaus.setId(id);
        return tilausRepository.save(editedTilaus);
    }

    // Delete tilaus
    @DeleteMapping("{id}")
    public Iterable<Tilaus> deleteTilaus(@PathVariable Long id) {
        tilausRepository.deleteById(id);
        return tilausRepository.findAll();
    }

}
