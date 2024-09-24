package com.drinkkiluostari.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tuotteet")
public class Tuote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nimi;
    private double hinta;

    @ManyToOne
    @JoinColumn(name = "kategoria_id")
    private Kategoria kategoria;

    public Tuote() {
    }

    public Tuote(String nimi, double hinta, Kategoria kategoria) {
        this.nimi = nimi;
        this.hinta = hinta;
        this.kategoria = kategoria;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public double getHinta() {
        return hinta;
    }

    public void setHinta(double hinta) {
        this.hinta = hinta;
    }

    public Kategoria getKategoria() {
        return kategoria;
    }

    public void setKategoria(Kategoria kategoria) {
        this.kategoria = kategoria;
    }

    @Override
    public String toString() {
        return "Tuote [id=" + id + ", nimi=" + nimi + ", hinta=" + hinta + ", kategoria=" + kategoria + "]";
    }

}
