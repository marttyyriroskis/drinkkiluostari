package com.drinkkiluostari.backend.domain;

import com.drinkkiluostari.backend.dto.KategoriaDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "kategoriat")
public class Kategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 100)
    private String nimi;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kategoria")
    private List<Tuote> tuotteet;

    public Kategoria() {
    }

    public Kategoria(String nimi) {
        this.nimi = nimi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public List<Tuote> getTuotteet() {
        return tuotteet;
    }

    public void setTuotteet(List<Tuote> tuotteet) {
        this.tuotteet = tuotteet;
    }

    public KategoriaDTO toDTO() {
        return new KategoriaDTO(
            this.id,
            this.nimi,
            
            this.tuotteet.stream()
                .map(Tuote::getId)
                .collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return "Kategoria [id=" + id + ", nimi=" + nimi + ", tuotteet=" + tuotteet + "]";
    }

}
