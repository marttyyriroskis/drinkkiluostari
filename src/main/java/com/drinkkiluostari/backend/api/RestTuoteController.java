package com.drinkkiluostari.backend.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import com.drinkkiluostari.backend.domain.Kategoria;
import com.drinkkiluostari.backend.domain.Tuote;
import com.drinkkiluostari.backend.dto.TuoteDTO;
import com.drinkkiluostari.backend.repository.KategoriaRepository;
import com.drinkkiluostari.backend.repository.TuoteRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tuotteet")
@Validated
public class RestTuoteController {
    private final TuoteRepository tuoteRepository;
    private final KategoriaRepository kategoriaRepository;
        
    public RestTuoteController(TuoteRepository tuoteRepository, KategoriaRepository kategoriaRepository) {
        this.tuoteRepository = tuoteRepository;
        this.kategoriaRepository = kategoriaRepository;
    }

    // Get tuotteet
    @GetMapping("")
    public ResponseEntity<List<TuoteDTO>> getTuotteet() {
        Iterable<Tuote> iterableTuotteet = tuoteRepository.findAllActive();
        List<Tuote> tuoteList = new ArrayList<>();
        iterableTuotteet.forEach(tuoteList::add);

        return ResponseEntity.ok(tuoteList.stream()
                .map(Tuote::toDTO)
                .toList());
    }
    
    // Get tuote by id
    @GetMapping("/{id}")
    public ResponseEntity<TuoteDTO> getTuote(@PathVariable Long id) {
        Tuote tuote = tuoteRepository.findByIdActive(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tuote not found"));
        return ResponseEntity.ok(tuote.toDTO());
    }
    
    // Post a new tuote
    @PostMapping
    public ResponseEntity<TuoteDTO> newTuote(@Valid @RequestBody TuoteDTO tuoteDTO) {
        Kategoria kategoria = kategoriaRepository.findById(tuoteDTO.kategoria().id())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Kategoria not found"));

        Tuote tuote = new Tuote();
        tuote.setNimi(tuoteDTO.nimi());
        tuote.setHinta(tuoteDTO.hinta());
        tuote.setKategoria(kategoria);

        tuoteRepository.save(tuote);

        return ResponseEntity.status(HttpStatus.CREATED).body(tuote.toDTO());
    }
    
    // Edit tuote
    @PutMapping("/{id}")
    public ResponseEntity<TuoteDTO> editTuote(@Valid @RequestBody TuoteDTO tuoteDTO, @PathVariable Long id) {
        Tuote tuote = tuoteRepository.findByIdActive(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tuote not found"));

        Kategoria kategoria = kategoriaRepository.findById(tuoteDTO.kategoria().id())
            .orElseThrow(() -> new IllegalArgumentException("Invalid Kategoria ID"));

        tuote.setNimi(tuoteDTO.nimi());
        tuote.setHinta(tuoteDTO.hinta());
        tuote.setKategoria(kategoria);

        Tuote updatedTuote = tuoteRepository.save(tuote);

        return ResponseEntity.ok(updatedTuote.toDTO());
    }

    // Delete tuote
    @DeleteMapping("/{id}")
    public ResponseEntity<Tuote> deleteTuote(@PathVariable Long id) {
        Tuote tuote = tuoteRepository.findByIdActive(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tuote not found"));
        
        tuote.delete();

        tuoteRepository.save(tuote);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
