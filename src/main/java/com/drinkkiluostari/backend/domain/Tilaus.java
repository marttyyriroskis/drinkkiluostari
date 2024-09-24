package com.drinkkiluostari.backend.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tilaukset")
public class Tilaus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime pvm;

    @ManyToOne
    @JoinColumn(name = "tyontekija_id")
    private Tyontekija tyontekija;

    @ManyToOne
    @JoinColumn(name = "asiakas_id")
    private Asiakas asiakas;

    public Tilaus() {
    }

    public Tilaus(LocalDateTime pvm, Tyontekija tyontekija, Asiakas asiakas) {
        this.pvm = pvm;
        this.tyontekija = tyontekija;
        this.asiakas = asiakas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public String toString() {
        return "Tilaus [id=" + id + ", pvm=" + pvm + ", tyontekija=" + tyontekija + ", asiakas=" + asiakas + "]";
    }

}
