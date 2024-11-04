package com.drinkkiluostari.backend.domain;

import java.time.LocalDateTime;

import com.drinkkiluostari.backend.dto.AsiakasDTO;
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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "asiakkaat")
public class Asiakas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 100)
    private String nimi;
    
    @Size(min = 1, max = 100)
    private String katuosoite;
    
    @NotNull
    private String yTunnus;

    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "postinumero")
    private Postinumero postinumero;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asiakas", orphanRemoval = true)
    @JsonIgnore
    private List<Tilaus> tilaukset;

    public Asiakas() {
    }

    public Asiakas(String nimi, String katuosoite, String yTunnus) {
        this.nimi = nimi;
        this.katuosoite = katuosoite;
        this.yTunnus = yTunnus;
    }

    public Asiakas(String nimi, String katuosoite, String yTunnus, Postinumero postinumero) {
        this.nimi = nimi;
        this.katuosoite = katuosoite;
        this.yTunnus = yTunnus;
        this.postinumero = postinumero;
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

    public String getKatuosoite() {
        return katuosoite;
    }

    public void setKatuosoite(String katuosoite) {
        this.katuosoite = katuosoite;
    }

    public String getyTunnus() {
        return yTunnus;
    }

    public void setyTunnus(String yTunnus) {
        this.yTunnus = yTunnus;
    }

    public Postinumero getPostinumero() {
        return postinumero;
    }

    public void setPostinumero(Postinumero postinumero) {
        this.postinumero = postinumero;
    }

    public List<Tilaus> getTilaukset() {
        return tilaukset;
    }

    public void setTilaukset(List<Tilaus> tilaukset) {
        this.tilaukset = tilaukset;
    }    

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void delete() {
        deletedAt = LocalDateTime.now();
    }

    public AsiakasDTO toDTO() {
        return new AsiakasDTO(
            this.id,
            this.nimi,
            this.katuosoite,
            this.yTunnus,
            this.postinumero.toDTO(),

            this.tilaukset.stream()
                .map(Tilaus::getId)
                .collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return "Asiakas [id=" + id + ", nimi=" + nimi + ", katuosoite=" + katuosoite + ", yTunnus=" + yTunnus
                + ", deletedAt=" + deletedAt + ", postinumero=" + postinumero + ", tilaukset=" + tilaukset + "]";
    }

    

}
