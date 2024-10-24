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

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "tyontekijat")
public class Tyontekija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String etunimi, sukunimi, salasana;

    @Column(name = "sahkoposti", unique = true)
    private String sahkoposti;

    @ManyToOne
    @JoinColumn(name = "rooli_id")
    private Rooli rooli;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tyontekija", orphanRemoval = true)
    @JsonIgnore
    private List<Tilaus> tilaukset;

    public Tyontekija() {
        // TODO: Generate sahkoposti from etunimi + sukunimi
    }

    public Tyontekija(String etunimi, String sukunimi, String sahkoposti, String salasana) {
        // TODO: Generate sahkoposti from etunimi + sukunimi
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.sahkoposti = sahkoposti;
        this.salasana = salasana;
    }

    public Tyontekija(String etunimi, String sukunimi, String sahkoposti, String salasana, Rooli rooli,
            List<Tilaus> tilaukset) {
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.sahkoposti = sahkoposti;
        this.salasana = salasana;
        this.rooli = rooli;
        this.tilaukset = tilaukset;
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
        this.sahkoposti = sahkoposti;
    }

    public String getSalasana() {
        return salasana;
    }

    public void setSalasana(String salasana) {
        this.salasana = salasana;
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
        return new TyontekijaDTO(this.etunimi,
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
        return "Tyontekija [id=" + id + ", etunimi=" + etunimi + ", sukunimi=" + sukunimi + ", sahkoposti=" + sahkoposti
                + ", salasana=" + salasana + ", rooli=" + rooli + ", tilaukset=" + tilaukset + "]";
    }

}
