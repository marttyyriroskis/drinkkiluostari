package com.drinkkiluostari.backend.api;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

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

import com.drinkkiluostari.backend.domain.Asiakas;
import com.drinkkiluostari.backend.dto.AsiakasDTO;
import com.drinkkiluostari.backend.repository.AsiakasRepository;
import com.drinkkiluostari.backend.domain.Postinumero;
import com.drinkkiluostari.backend.repository.PostinumeroRepository;
import com.drinkkiluostari.backend.domain.Tilaus;
import com.drinkkiluostari.backend.repository.TilausRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/asiakkaat")
@Validated
public class RestAsiakasController {
    private final AsiakasRepository asiakasRepository;
    private final PostinumeroRepository postinumeroRepository;
    private final TilausRepository tilausRepository;
    
    public RestAsiakasController(AsiakasRepository asiakasRepository, PostinumeroRepository postinumeroRepository, TilausRepository tilausRepository) {
        this.asiakasRepository = asiakasRepository;
        this.postinumeroRepository = postinumeroRepository;
        this.tilausRepository = tilausRepository;
    }

    // Get asiakkaat
    @GetMapping
    public ResponseEntity<List<AsiakasDTO>> getAsiakkaat() {
        Iterable<Asiakas> iterableAsiakkaat = asiakasRepository.findAllActive();
        List<Asiakas> asiakasList = new ArrayList<>();
        iterableAsiakkaat.forEach(asiakasList::add);

        return ResponseEntity.ok(asiakasList.stream()
                .map(Asiakas::toDTO)
                .toList());
    }
    
    // Get asiakas by id
    @GetMapping("{id}")
    public Asiakas getAsiakas(@PathVariable("id") Long asiakasId) {
        Asiakas asiakas = asiakasRepository.findById(asiakasId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Asiakas not found"));
        return asiakas;
    }
    
    // Post a new asiakas
    @PostMapping("")
    public Asiakas newAsiakas(@Valid @RequestBody Asiakas asiakas) {
        if (asiakas.getPostinumero() != null && asiakas.getPostinumero().getId() == null) {
            asiakas.setPostinumero(null);
        }

        if (asiakas.getPostinumero() != null) {
            Optional<Postinumero> existingPostinumero = postinumeroRepository.findById(asiakas.getPostinumero().getId());
            if (!existingPostinumero.isPresent()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Postinumero does not exist!");
            }
            asiakas.setPostinumero(existingPostinumero.get());
        }

        List<Tilaus> validTilaukset = new ArrayList<>();
        List<Tilaus> requestTilaukset = asiakas.getTilaukset();
        for (Tilaus requestTilaus : requestTilaukset) {
            Optional<Tilaus> tilaus = tilausRepository.findById(requestTilaus.getId());
            if (!tilaus.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid tilaus");
            }
            validTilaukset.add(tilaus.get());
            tilaus.get().setAsiakas(asiakas);
        }
        if (validTilaukset.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid tilaukset in Asiakas");
        }

        asiakas.setTilaukset(validTilaukset);
        return asiakasRepository.save(asiakas);
    }
    
    // Edit asiakas
    @PutMapping("{id}")
    public Asiakas editAsiakas(@Valid @RequestBody Asiakas editedAsiakas, @PathVariable Long asiakasId) {
        Asiakas asiakas = asiakasRepository.findById(asiakasId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Asiakas not found"));

        Postinumero editedPostinumero = editedAsiakas.getPostinumero();
        if (editedPostinumero != null) {
            Optional<Postinumero> existingPostinumero = postinumeroRepository.findById(editedPostinumero.getId());
            if (!existingPostinumero.isPresent()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Invalid postinumero");
            }
            asiakas.setPostinumero(existingPostinumero.get());
        } else {
            asiakas.setPostinumero(null);
        }

        List<Tilaus> validTilaukset = new ArrayList<>();
        for (Tilaus requestTilaus : editedAsiakas.getTilaukset()) {
            Optional<Tilaus> tilaus = tilausRepository.findById(requestTilaus.getId());
            if (!tilaus.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid tilaus");
            }
            validTilaukset.add(tilaus.get());
            tilaus.get().setAsiakas(asiakas);
        }

        asiakas.setNimi(editedAsiakas.getNimi());
        asiakas.setKatuosoite(editedAsiakas.getKatuosoite());
        asiakas.setyTunnus(editedAsiakas.getyTunnus());
        asiakas.setTilaukset(validTilaukset);

        return asiakasRepository.save(asiakas);
    }

    // Delete asiakas
    @DeleteMapping("{id}")
    public Iterable<Asiakas> deleteAsiakas(@PathVariable ("id") Long asiakasId) {
        Optional<Asiakas> optionalAsiakas = asiakasRepository.findById(asiakasId);
        if (!optionalAsiakas.isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Asiakas not found");
        }

        asiakasRepository.deleteById(asiakasId);
        return asiakasRepository.findAll();
    }

}
