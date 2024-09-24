package com.drinkkiluostari.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tilausrivit")
public class Tilausrivi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double hinta, alennus;
    private int maara;

    @ManyToOne
    @JoinColumn(name = "tuote_id")
    private Tuote tuote;

    @ManyToOne
    @JoinColumn(name = "tilaus_id")
    private Tilaus tilaus;

    public Tilausrivi() {
    }

    public Tilausrivi(double hinta, double alennus, int maara, Tuote tuote, Tilaus tilaus) {
        this.hinta = hinta;
        this.alennus = alennus;
        this.maara = maara;
        this.tuote = tuote;
        this.tilaus = tilaus;
    }

    public double getHinta() {
        return hinta;
    }

    public void setHinta(double hinta) {
        this.hinta = hinta;
    }

    public double getAlennus() {
        return alennus;
    }

    public void setAlennus(double alennus) {
        this.alennus = alennus;
    }

    public int getMaara() {
        return maara;
    }

    public void setMaara(int maara) {
        this.maara = maara;
    }

    public Tuote getTuote() {
        return tuote;
    }

    public void setTuote(Tuote tuote) {
        this.tuote = tuote;
    }

    public Tilaus getTilaus() {
        return tilaus;
    }

    public void setTilaus(Tilaus tilaus) {
        this.tilaus = tilaus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Tilausrivit [id=" + id + ", hinta=" + hinta + ", alennus=" + alennus + ", maara=" + maara + ", tuote="
                + tuote + ", tilaus=" + tilaus + "]";
    }

}
