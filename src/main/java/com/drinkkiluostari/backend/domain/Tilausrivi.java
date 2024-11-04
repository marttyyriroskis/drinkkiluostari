package com.drinkkiluostari.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;

import com.drinkkiluostari.backend.dto.TilausriviDTO;

@Entity
@Table(name = "tilausrivit")
public class Tilausrivi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @PositiveOrZero
    private double hinta;
    
    @NotEmpty
    @PositiveOrZero
    private double alennus;

    @NotEmpty
    @PositiveOrZero
    private int maara;

    @ManyToOne
    @JoinColumn(name = "tuote_id")
    private Tuote tuote;

    @ManyToOne
    @JoinColumn(name = "tilaus_id")
    private Tilaus tilaus;

    public Tilausrivi() {
    }

    public Tilausrivi(double hinta, double alennus, int maara) {
        this.hinta = hinta;
        this.alennus = alennus;
        this.maara = maara;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TilausriviDTO toDTO() {
        return new TilausriviDTO(
            this.hinta,
            this.alennus,
            this.maara,
            this.tuote.toDTO(),
            this.tilaus.toDTO());
    }

    @Override
    public String toString() {
        return "Tilausrivit [id=" + id + ", hinta=" + hinta + ", alennus=" + alennus + ", maara=" + maara + ", tuote="
                + tuote + ", tilaus=" + tilaus + "]";
    }

}
