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
@Table(name = "postinumerot")
public class Postinumero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postinumero, postitoimipaikka;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "postinumero")
    @JsonIgnore
    private List<Asiakas> asiakkaat;

    public Postinumero() {
    }

    public Postinumero(String postinumero, String postitoimipaikka) {
        this.postinumero = postinumero;
        this.postitoimipaikka = postitoimipaikka;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostinumero() {
        return postinumero;
    }

    public void setPostinumero(String postinumero) {
        this.postinumero = postinumero;
    }

    public String getPostitoimipaikka() {
        return postitoimipaikka;
    }

    public void setPostitoimipaikka(String postitoimipaikka) {
        this.postitoimipaikka = postitoimipaikka;
    }

    public List<Asiakas> getAsiakkaat() {
        return asiakkaat;
    }

    public void setAsiakkaat(List<Asiakas> asiakkaat) {
        this.asiakkaat = asiakkaat;
    }

    @Override
    public String toString() {
        return "Postinumero [id=" + id + ", postinumero=" + postinumero + ", postitoimipaikka=" + postitoimipaikka
                + ", asiakkaat=" + asiakkaat + "]";
    }
    
}
