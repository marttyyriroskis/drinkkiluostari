package com.drinkkiluostari.backend.api;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.drinkkiluostari.backend.dto.CreateAsiakasDTO;
import com.drinkkiluostari.backend.repository.AsiakasRepository;
import com.drinkkiluostari.backend.domain.Postinumero;
import com.drinkkiluostari.backend.repository.PostinumeroRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/asiakkaat")
public class RestAsiakasController {
    private final AsiakasRepository asiakasRepository;
    private final PostinumeroRepository postinumeroRepository;
    
    public RestAsiakasController(AsiakasRepository asiakasRepository, PostinumeroRepository postinumeroRepository) {
        this.asiakasRepository = asiakasRepository;
        this.postinumeroRepository = postinumeroRepository;
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
    @GetMapping("/{id}")
    public ResponseEntity<AsiakasDTO> getAsiakas(@PathVariable Long id) {
        Asiakas asiakas = asiakasRepository.findByIdActive(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Asiakas not found"));
        return ResponseEntity.ok(asiakas.toDTO());
    }
    
    // Post a new asiakas
    @PostMapping
    public ResponseEntity<CreateAsiakasDTO> newAsiakas(@Valid @RequestBody AsiakasDTO asiakasDTO) {
        Postinumero postinumero = postinumeroRepository.findById(asiakasDTO.postinumero().id())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Postinumero not found"));

        Asiakas asiakas = new Asiakas();
        asiakas.setNimi(asiakasDTO.nimi());
        asiakas.setKatuosoite(asiakasDTO.katuosoite());
        asiakas.setyTunnus(asiakasDTO.yTunnus());
        asiakas.setPostinumero(postinumero);

        Asiakas savedAsiakas = asiakasRepository.save(asiakas);

        CreateAsiakasDTO newAsiakas = new CreateAsiakasDTO(
                savedAsiakas.getId(),
                savedAsiakas.getNimi(),
                savedAsiakas.getKatuosoite(),
                savedAsiakas.getyTunnus(),
                savedAsiakas.getPostinumero().toDTO());

        return ResponseEntity.status(HttpStatus.CREATED).body(newAsiakas);
    }
    
    // Edit asiakas
    @PutMapping("/{id}")
    public ResponseEntity<AsiakasDTO> editAsiakas(@Valid @RequestBody AsiakasDTO asiakasDTO, @PathVariable Long id) {
        Asiakas asiakas = asiakasRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Asiakas not found"));

        Postinumero postinumero = postinumeroRepository.findById(asiakasDTO.postinumero().id())
            .orElseThrow(() -> new IllegalArgumentException("Invalid Postinumero ID"));

        asiakas.setNimi(asiakasDTO.nimi());
        asiakas.setKatuosoite(asiakasDTO.katuosoite());
        asiakas.setyTunnus(asiakasDTO.yTunnus());
        asiakas.setPostinumero(postinumero);
        
        Asiakas updatedAsiakas = asiakasRepository.save(asiakas);

        return ResponseEntity.ok(updatedAsiakas.toDTO());
    }

    // Delete asiakas
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Asiakas> deleteAsiakas(@PathVariable Long id) {
        Asiakas asiakas = asiakasRepository.findByIdActive(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Asiakas not found"));

        asiakas.delete();

        asiakasRepository.save(asiakas);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
