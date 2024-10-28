package com.drinkkiluostari.backend.api;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.drinkkiluostari.backend.domain.Tilaus;
import com.drinkkiluostari.backend.repository.TilausRepository;
import com.drinkkiluostari.backend.domain.Asiakas;
import com.drinkkiluostari.backend.repository.AsiakasRepository;
import com.drinkkiluostari.backend.domain.Tyontekija;
import com.drinkkiluostari.backend.dto.TilausDTO;
import com.drinkkiluostari.backend.repository.TyontekijaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tilaukset")
@Validated
public class RestTilausController {
    private final TilausRepository tilausRepository;
    private final AsiakasRepository asiakasRepository;
    private final TyontekijaRepository tyontekijaRepository;
    
    public RestTilausController(TilausRepository tilausRepository, AsiakasRepository asiakasRepository, TyontekijaRepository tyontekijaRepository) {
        this.tilausRepository = tilausRepository;
        this.asiakasRepository = asiakasRepository;
        this.tyontekijaRepository = tyontekijaRepository;
    }

    // Get tilaukset
    @GetMapping
    public ResponseEntity<List<TilausDTO>> getTilaukset() {
        Iterable<Tilaus> iterableTilaukset = tilausRepository.findAllActive();
        List<Tilaus> tilausList = new ArrayList<>();
        iterableTilaukset.forEach(tilausList::add);

        return ResponseEntity.ok(tilausList.stream()
                .map(Tilaus::toDTO)
                .toList());
    }
    
    // Get tilaus by id
    @GetMapping("/{id}")
    public ResponseEntity<TilausDTO> getTilaus(@PathVariable Long id) {
        Tilaus tilaus = tilausRepository.findByIdActive(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tilaus not found"));
        return ResponseEntity.ok(tilaus.toDTO());
    }
    
    // Post a new tilaus
    @PostMapping
    public ResponseEntity<TilausDTO> newTilaus(@Valid @RequestBody TilausDTO tilausDTO) {
        Tyontekija tyontekija = tyontekijaRepository.findById(tilausDTO.tyontekija().id())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Tyontekija not found"));

        Asiakas asiakas = asiakasRepository.findById(tilausDTO.asiakas().id())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Asiakas not found"));

        Tilaus tilaus = new Tilaus();
        
        tilaus.setPvm(LocalDateTime.now());
        tilaus.setTyontekija(tyontekija);
        tilaus.setAsiakas(asiakas);

        tilausRepository.save(tilaus);

        return ResponseEntity.status(HttpStatus.CREATED).body(tilaus.toDTO());
    }
    
    // Edit tilaus
    @PutMapping("/{id}")
    public ResponseEntity<TilausDTO> editTilaus(@Valid @RequestBody TilausDTO tilausDTO, @PathVariable Long id) {
        Tilaus tilaus = tilausRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tilaus not found"));

        Tyontekija tyontekija = tyontekijaRepository.findById(tilausDTO.tyontekija().id())
            .orElseThrow(() -> new IllegalArgumentException("Invalid Tyontekija ID"));

        Asiakas asiakas = asiakasRepository.findById(tilausDTO.asiakas().id())
            .orElseThrow(() -> new IllegalArgumentException("Invalid Asiakas ID"));

        tilaus.edit();
        tilaus.setTyontekija(tyontekija);
        tilaus.setAsiakas(asiakas);
        
        Tilaus updatedTilaus = tilausRepository.save(tilaus);

        return ResponseEntity.ok(updatedTilaus.toDTO());
    }

    // Delete tilaus
    @DeleteMapping("/{id}")
    public ResponseEntity<Tilaus> deleteTilaus(@PathVariable Long id) {
        Tilaus tilaus = tilausRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tilaus not found"));
        
        tilaus.delete();

        tilausRepository.save(tilaus);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
