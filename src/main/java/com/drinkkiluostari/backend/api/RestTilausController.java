package com.drinkkiluostari.backend.api;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.http.HttpStatus;
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
import com.drinkkiluostari.backend.domain.Tilausrivi;
import com.drinkkiluostari.backend.repository.TilausRepository;
import com.drinkkiluostari.backend.repository.TilausriviRepository;
import com.drinkkiluostari.backend.domain.Asiakas;
import com.drinkkiluostari.backend.repository.AsiakasRepository;
import com.drinkkiluostari.backend.domain.Tyontekija;
import com.drinkkiluostari.backend.repository.TyontekijaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tilaukset")
@Validated
public class RestTilausController {
    private final TilausRepository tilausRepository;
    private final AsiakasRepository asiakasRepository;
    private final TyontekijaRepository tyontekijaRepository;
    private final TilausriviRepository tilausriviRepository;
    
    public RestTilausController(TilausRepository tilausRepository, AsiakasRepository asiakasRepository, TyontekijaRepository tyontekijaRepository,
    TilausriviRepository tilausriviRepository) {
        this.tilausRepository = tilausRepository;
        this.asiakasRepository = asiakasRepository;
        this.tyontekijaRepository = tyontekijaRepository;
        this.tilausriviRepository = tilausriviRepository;
    }

    // Get tilaukset
    @GetMapping("")
    public Iterable<Tilaus> getTilaukset() {
        return tilausRepository.findAll();
    }
    
    // Get tilaus by id
    @GetMapping("{id}")
    public Tilaus getTilaus(@PathVariable("id") Long tilausId) {
        Tilaus tilaus = tilausRepository.findById(tilausId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tilaus not found"));
        return tilaus;
    }
    
    // Post a new tilaus
    @PostMapping("")
    public Tilaus newTilaus(@Valid @RequestBody Tilaus tilaus) {
        if (tilaus.getTyontekija() != null && tilaus.getTyontekija().getId() == null) {
            tilaus.setTyontekija(null);
        }

        if (tilaus.getTyontekija() != null) {
            Optional<Tyontekija> existingTyontekija = tyontekijaRepository.findById(tilaus.getTyontekija().getId());
            if (!existingTyontekija.isPresent()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Tyontekija does not exist!");
            }
            tilaus.setTyontekija(existingTyontekija.get());
        }

        if (tilaus.getAsiakas() != null && tilaus.getAsiakas().getId() == null) {
            tilaus.setAsiakas(null);
        }

        if (tilaus.getAsiakas() != null) {
            Optional<Asiakas> existingAsiakas = asiakasRepository.findById(tilaus.getAsiakas().getId());
            if (!existingAsiakas.isPresent()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Asiakas does not exist!");
            }
            tilaus.setAsiakas(existingAsiakas.get());
        }

        List<Tilausrivi> validTilausrivit = new ArrayList<>();
        List<Tilausrivi> requestTilausrivit = tilaus.getTilausrivit();
        for (Tilausrivi requestTilausrivi : requestTilausrivit) {
            Optional<Tilausrivi> tilausrivi = tilausriviRepository.findById(requestTilausrivi.getId());
            if (!tilausrivi.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid tilausrivi");
            }
            validTilausrivit.add(tilausrivi.get());
            tilausrivi.get().setTilaus(tilaus);
        }
        if (validTilausrivit.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid tilausrivit in Tilaus");
        }

        tilaus.setTilausrivit(validTilausrivit);
        return tilausRepository.save(tilaus);
    }
    
    // Edit tilaus
    @PutMapping("{id}")
    public Tilaus editTilaus(@Valid @RequestBody Tilaus editedTilaus, @PathVariable Long tilausId) {
        Tilaus tilaus = tilausRepository.findById(tilausId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tilaus not found"));

        Tyontekija editedTyontekija = editedTilaus.getTyontekija();
        if (editedTyontekija != null) {
            Optional<Tyontekija> existingTyontekija = tyontekijaRepository.findById(editedTilaus.getTyontekija().getId());
            if (!existingTyontekija.isPresent()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Invalid työntekijä");
            }
            tilaus.setTyontekija(existingTyontekija.get());
        } else {
            tilaus.setTyontekija(null);
        }

        Asiakas editedAsiakas = editedTilaus.getAsiakas();
        if (editedAsiakas != null) {
            Optional<Asiakas> existingAsiakas = asiakasRepository.findById(editedTilaus.getAsiakas().getId());
            if (!existingAsiakas.isPresent()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Invalid asiakas");
            }
            tilaus.setAsiakas(existingAsiakas.get());
        } else {
            tilaus.setAsiakas(null);
        }

        List<Tilausrivi> validTilausrivit = new ArrayList<>();
        for (Tilausrivi requestTilausrivi : editedTilaus.getTilausrivit()) {
            Optional<Tilausrivi> tilausrivi = tilausriviRepository.findById(requestTilausrivi.getId());
            if (!tilausrivi.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid tilausrivi");
            }
            validTilausrivit.add(tilausrivi.get());
            tilausrivi.get().setTilaus(tilaus);
        }
        if (validTilausrivit.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid tilausrivit in Tilaus");
        }

        tilaus.setPvm(editedTilaus.getPvm());
        tilaus.setTilausrivit(validTilausrivit);

        return tilausRepository.save(tilaus);
    }

    // Delete tilaus
    @DeleteMapping("{id}")
    public Iterable<Tilaus> deleteTilaus(@PathVariable ("id") Long tilausId) {
        Optional<Tilaus> optionalTilaus = tilausRepository.findById(tilausId);
        if (!optionalTilaus.isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Tilaus not found");
        }

        tilausRepository.deleteById(tilausId);
        return tilausRepository.findAll();
    }

}
