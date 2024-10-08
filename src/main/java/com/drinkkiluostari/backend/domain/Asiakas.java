package com.drinkkiluostari.backend.domain;

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
@Table(name = "asiakkaat")
public class Asiakas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nimi, katuosoite, y_tunnus;

    @ManyToOne
    @JoinColumn(name = "postinumero")
    private Postinumero postinumero;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asiakas")
    @JsonIgnore
    private List<Tilaus> tilaukset;

    public Asiakas() {
    }

    public Asiakas(String nimi, String katuosoite, String y_tunnus) {
        this.nimi = nimi;
        this.katuosoite = katuosoite;
        this.y_tunnus = y_tunnus;
    }

    public Asiakas(String nimi, String katuosoite, String y_tunnus, Postinumero postinumero, List<Tilaus> tilaukset) {
        this.nimi = nimi;
        this.katuosoite = katuosoite;
        this.y_tunnus = y_tunnus;
        this.postinumero = postinumero;
        this.tilaukset = tilaukset;
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

    public String getY_tunnus() {
        return y_tunnus;
    }

    public void setY_tunnus(String y_tunnus) {
        this.y_tunnus = y_tunnus;
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

    @Override
    public String toString() {
        return "Asiakas [id=" + id + ", nimi=" + nimi + ", katuosoite=" + katuosoite + ", y_tunnus=" + y_tunnus
                + ", postinumero=" + postinumero + ", tilaukset=" + tilaukset + "]";
    }

}
