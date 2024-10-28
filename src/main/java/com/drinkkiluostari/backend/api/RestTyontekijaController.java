package com.drinkkiluostari.backend.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.drinkkiluostari.backend.domain.Rooli;
import com.drinkkiluostari.backend.domain.Tyontekija;
import com.drinkkiluostari.backend.dto.TyontekijaDTO;
import com.drinkkiluostari.backend.repository.RooliRepository;
import com.drinkkiluostari.backend.repository.TyontekijaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tyontekijat")
@Validated
public class RestTyontekijaController {
    private final TyontekijaRepository tyontekijaRepository;
    private final RooliRepository rooliRepository;
        
    public RestTyontekijaController(TyontekijaRepository tyontekijaRepository, RooliRepository rooliRepository) {
        this.tyontekijaRepository = tyontekijaRepository;
        this.rooliRepository = rooliRepository;
    }

    // Get tyontekijat
    @GetMapping
    public ResponseEntity<List<TyontekijaDTO>> getTyontekijat() {
        Iterable<Tyontekija> iterableTyontekijat = tyontekijaRepository.findAllActive();
        List<Tyontekija> tyontekijaList = new ArrayList<>();
        iterableTyontekijat.forEach(tyontekijaList::add);

        return ResponseEntity.ok(tyontekijaList.stream()
                .map(Tyontekija::toDTO)
                .toList());
    }
    
    // Get tyontekija by id
    @GetMapping("/{id}")
    public ResponseEntity<TyontekijaDTO> getTyontekija(@PathVariable Long id) {
        Tyontekija tyontekija = tyontekijaRepository.findByIdActive(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tyontekija not found"));
        return ResponseEntity.ok(tyontekija.toDTO());
    }
    
    // Post a new tyontekija
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TyontekijaDTO> newTyontekija(@Valid @RequestBody TyontekijaDTO tyontekijaDTO) {
        Rooli rooli = rooliRepository.findById(tyontekijaDTO.rooli().id())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Rooli not found"));

        Tyontekija tyontekija = new Tyontekija();
        tyontekija.setEtunimi(tyontekijaDTO.etunimi());
        tyontekija.setSukunimi(tyontekijaDTO.sukunimi());
        tyontekija.setSalasana(tyontekijaDTO.salasana());
        tyontekija.setRooli(rooli);

        tyontekijaRepository.save(tyontekija);

        return ResponseEntity.status(HttpStatus.CREATED).body(tyontekija.toDTO());
    }
    
    // Edit tyontekija
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TyontekijaDTO> editTyontekija(@Valid @RequestBody TyontekijaDTO tyontekijaDTO, @PathVariable Long id) {
        Tyontekija tyontekija = tyontekijaRepository.findByIdActive(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tyontekija not found"));

        Rooli rooli = rooliRepository.findById(tyontekijaDTO.rooli().id())
            .orElseThrow(() -> new IllegalArgumentException("Invalid Rooli ID"));

        tyontekija.setEtunimi(tyontekijaDTO.etunimi());
        tyontekija.setSukunimi(tyontekijaDTO.sukunimi());
        tyontekija.setSalasana(tyontekijaDTO.salasana());
        tyontekija.setRooli(rooli);

        Tyontekija updatedTyontekija = tyontekijaRepository.save(tyontekija);

        return ResponseEntity.ok(updatedTyontekija.toDTO());
    }

    // Delete tyontekija
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Tyontekija> deleteTyontekija(@PathVariable Long id) {
        Tyontekija tyontekija = tyontekijaRepository.findByIdActive(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tyontekija not found"));
        
        tyontekija.delete();

        tyontekijaRepository.save(tyontekija);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
