package com.drinkkiluostari.backend.domain;

import com.drinkkiluostari.backend.dto.TyontekijaDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "tyontekijat")
public class Tyontekija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 50)
    private String etunimi;
    
    @Size(min = 1, max = 50)
    private String sukunimi;
    
    @NotNull
    private String salasana;

    @Size(min = 1, max = 100)
    @Column(name = "sahkoposti", unique = true)
    private String sahkoposti;

    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "rooli_id")
    private Rooli rooli;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tyontekija", orphanRemoval = true)
    @JsonIgnore
    private List<Tilaus> tilaukset;

    public Tyontekija() {
    }

    public Tyontekija(String etunimi, String sukunimi, String sahkoposti, String salasana) {
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.sahkoposti = etunimi + "." + sukunimi + "@sahkoposti.fi";
        this.salasana = salasana;
    }

    public Tyontekija(String etunimi, String sukunimi, String sahkoposti, String salasana, Rooli rooli) {
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.sahkoposti = etunimi + "." + sukunimi + "@sahkoposti.fi";
        this.salasana = salasana;
        this.rooli = rooli;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEtunimi() {
        return etunimi;
    }

    public void setEtunimi(String etunimi) {
        this.etunimi = etunimi;
    }

    public String getSukunimi() {
        return sukunimi;
    }

    public void setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
    }

    public String getSahkoposti() {
        return sahkoposti;
    }

    public void setSahkoposti(String sahkoposti) {
        this.sahkoposti = etunimi + "." + sukunimi + "@sahkoposti.fi";
    }

    public String getSalasana() {
        return salasana;
    }

    public void setSalasana(String salasana) {
        this.salasana = salasana;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void delete() {
        deletedAt = LocalDateTime.now();
    }

    public Rooli getRooli() {
        return rooli;
    }

    public void setRooli(Rooli rooli) {
        this.rooli = rooli;
    }

    public List<Tilaus> getTilaukset() {
        return tilaukset;
    }

    public void setTilaukset(List<Tilaus> tilaukset) {
        this.tilaukset = tilaukset;
    }
    
    public TyontekijaDTO toDTO() {
        return new TyontekijaDTO(
            this.id,
            this.etunimi,
            this.sukunimi,
            this.salasana,
            this.sahkoposti,
            this.rooli.toDTO(),
            
            this.tilaukset.stream()
                .map(Tilaus::getId)
                .collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return "Tyontekija [id=" + id + ", etunimi=" + etunimi + ", sukunimi=" + sukunimi + ", salasana=" + salasana
                + ", sahkoposti=" + sahkoposti + ", deletedAt=" + deletedAt + ", rooli=" + rooli + ", tilaukset="
                + tilaukset + "]";
    }

}
