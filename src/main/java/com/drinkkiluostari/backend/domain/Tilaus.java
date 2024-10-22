package com.drinkkiluostari.backend.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "tilaukset")
public class Tilaus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime pvm;

    @ManyToOne
    @JoinColumn(name = "tyontekija_id")
    private Tyontekija tyontekija;

    @ManyToOne
    @JoinColumn(name = "asiakas_id")
    private Asiakas asiakas;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tilaus", orphanRemoval = true)
    @JsonIgnore
    private List<Tilausrivi> tilausrivit;

    public Tilaus() {
    }

    public Tilaus(LocalDateTime pvm) {
        this.pvm = pvm;
    }

    public Tilaus(LocalDateTime pvm, Tyontekija tyontekija, Asiakas asiakas, List<Tilausrivi> tilausrivit) {
        this.pvm = pvm;
        this.tyontekija = tyontekija;
        this.asiakas = asiakas;
        this.tilausrivit = tilausrivit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getPvm() {
        return pvm;
    }

    public void setPvm(LocalDateTime pvm) {
        this.pvm = pvm;
    }

    public Tyontekija getTyontekija() {
        return tyontekija;
    }

    public void setTyontekija(Tyontekija tyontekija) {
        this.tyontekija = tyontekija;
    }

    public Asiakas getAsiakas() {
        return asiakas;
    }

    public void setAsiakas(Asiakas asiakas) {
        this.asiakas = asiakas;
    }

    public List<Tilausrivi> getTilausrivit() {
        return tilausrivit;
    }

    public void setTilausrivit(List<Tilausrivi> tilausrivit) {
        this.tilausrivit = tilausrivit;
    }
    
    @Override
    public String toString() {
        return "Tilaus [id=" + id + ", pvm=" + pvm + ", tyontekija=" + tyontekija + ", asiakas=" + asiakas
                + ", tilausrivit=" + tilausrivit + "]";
    }

}
