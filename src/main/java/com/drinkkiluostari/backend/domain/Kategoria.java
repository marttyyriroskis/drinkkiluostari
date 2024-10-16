package com.drinkkiluostari.backend.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "kategoriat")
public class Kategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nimi;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kategoria")
    @JsonIgnore
    private List<Tuote> tuotteet;

    public Kategoria() {
    }

    public Kategoria(String nimi) {
        this.nimi = nimi;
    }

    public Kategoria(String nimi, List<Tuote> tuotteet) {
        this.nimi = nimi;
        this.tuotteet = tuotteet;
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

    @Override
    public String toString() {
        return "Kategoria [id=" + id + ", nimi=" + nimi + ", tuotteet=" + tuotteet + "]";
    }

}
