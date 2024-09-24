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
    private long id;

    private String rooli;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rooli")
    @JsonIgnore
    private List<Tyontekija> tyontekijat;

    public Rooli() {
    }

    public Rooli(String rooli) {
        this.rooli = rooli;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRooli() {
        return rooli;
    }

    public void setRooli(String rooli) {
        this.rooli = rooli;
    }
    
    public List<Tyontekija> getTyontekijat() {
        return tyontekijat;
    }

    public void setTyontekijat(List<Tyontekija> tyontekijat) {
        this.tyontekijat = tyontekijat;
    }

    @Override
    public String toString() {
        return "Rooli [id=" + id + ", rooli=" + rooli + "]";
    }

}
