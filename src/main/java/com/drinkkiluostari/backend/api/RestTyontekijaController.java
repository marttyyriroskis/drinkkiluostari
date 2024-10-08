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

import com.drinkkiluostari.backend.domain.Tyontekija;
import com.drinkkiluostari.backend.repository.RooliRepository;
import com.drinkkiluostari.backend.repository.TyontekijaRepository;

@RestController
@RequestMapping("/api/tyontekijat")
public class RestTyontekijaController {
    @Autowired
    TyontekijaRepository tyontekijaRepository;

    @Autowired
    RooliRepository rooliRepository;

    // Get tyontekijat
    @GetMapping("")
    public Iterable<Tyontekija> getTyontekijat() {
        return tyontekijaRepository.findAll();
    }
    
    // Get tyontekija by id
    @GetMapping("{id}")
    public Optional<Tyontekija> getTyontekija(@PathVariable("id") Long tyontekijaId) {
        return tyontekijaRepository.findById(tyontekijaId);
    }
    
    // Post a new tyontekija
    @PostMapping("")
    public Tyontekija newTyontekija(@RequestBody Tyontekija newTyontekija) {
        return tyontekijaRepository.save(newTyontekija);
    }
    
    // Edit tyontekija
    @PutMapping("{id}")
    public Tyontekija editTyontekija(@RequestBody Tyontekija editedTyontekija, @PathVariable Long id) {
        editedTyontekija.setId(id);
        return tyontekijaRepository.save(editedTyontekija);
    }

    // Delete tyontekija
    @DeleteMapping("{id}")
    public Iterable<Tyontekija> deleteTyontekija(@PathVariable Long id) {
        tyontekijaRepository.deleteById(id);
        return tyontekijaRepository.findAll();
    }

}
