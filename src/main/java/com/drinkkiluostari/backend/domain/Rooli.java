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
@Table(name = "roolit")
public class Rooli {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nimi;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rooli")
    @JsonIgnore
    private List<Tyontekija> tyontekijat;

    public Rooli() {
    }

    public Rooli(String nimi) {
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
    
    public List<Tyontekija> getTyontekijat() {
        return tyontekijat;
    }

    public void setTyontekijat(List<Tyontekija> tyontekijat) {
        this.tyontekijat = tyontekijat;
    }

    @Override
    public String toString() {
        return "Rooli [id=" + id + ", nimi=" + nimi + "]";
    }

}
