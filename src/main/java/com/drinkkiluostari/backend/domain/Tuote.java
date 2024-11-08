package com.drinkkiluostari.backend.domain;

import com.drinkkiluostari.backend.dto.TuoteDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "tuotteet")
public class Tuote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 100)
    private String nimi;

    @NotNull
    @PositiveOrZero
    private double hinta;

    @JoinColumn(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "kategoria_id")
    private Kategoria kategoria;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tuote")
    private List<Tilausrivi> tilausrivit;

    public Tuote() {
    }

    public Tuote(String nimi, double hinta) {
        this.nimi = nimi;
        this.hinta = hinta;
    }

    public Tuote(String nimi, double hinta, Kategoria kategoria) {
        this.nimi = nimi;
        this.hinta = hinta;
        this.kategoria = kategoria;
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

    public double getHinta() {
        return hinta;
    }

    public void setHinta(double hinta) {
        this.hinta = hinta;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void delete() {
        deletedAt = LocalDateTime.now();
    }

    public Kategoria getKategoria() {
        return kategoria;
    }

    public void setKategoria(Kategoria kategoria) {
        this.kategoria = kategoria;
    }

    public List<Tilausrivi> getTilausrivit() {
        return tilausrivit;
    }

    public void setTilausrivit(List<Tilausrivi> tilausrivit) {
        this.tilausrivit = tilausrivit;
    }
    
    public TuoteDTO toDTO() {
        return new TuoteDTO(
            this.nimi,
            this.hinta,
            this.kategoria.toDTO(),

            this.tilausrivit.stream()
                .map(Tilausrivi::getId)
                .collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return "Tuote [id=" + id + ", nimi=" + nimi + ", hinta=" + hinta + ", deletedAt=" + deletedAt + ", kategoria="
                + kategoria + ", tilausrivit=" + tilausrivit + "]";
    }

}
