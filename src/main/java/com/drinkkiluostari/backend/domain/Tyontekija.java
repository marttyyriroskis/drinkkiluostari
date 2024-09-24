package com.drinkkiluostari.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tyontekijat")
public class Tyontekija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String etunimi, sukunimi, sahkoposti, salasana;

    @ManyToOne
    @JoinColumn(name = "rooli_id")
    private Rooli rooli;

    public Tyontekija() {
        // TODO: Generate sahkoposti from etunimi + sukunimi
    }

    public Tyontekija(String etunimi, String sukunimi, String sahkoposti, String salasana, Rooli rooli) {
        // TODO: Generate sahkoposti from etunimi + sukunimi
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.sahkoposti = sahkoposti;
        this.salasana = salasana;
        this.rooli = rooli;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEtunimi() {
        return etunimi;
    }

    public void setEtunimi(String etunimi) {
        this.etunimi = etunimi;
    }

    public String getSukunimi() {
        return sukunimi;
    }

    public void setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
    }

    public String getSahkoposti() {
        return sahkoposti;
    }

    public void setSahkoposti(String sahkoposti) {
        this.sahkoposti = sahkoposti;
    }

    public String getSalasana() {
        return salasana;
    }

    public void setSalasana(String salasana) {
        this.salasana = salasana;
    }

    public Rooli getRooli() {
        return rooli;
    }

    public void setRooli(Rooli rooli) {
        this.rooli = rooli;
    }

    @Override
    public String toString() {
        return "Tyontekija [id=" + id + ", etunimi=" + etunimi + ", sukunimi=" + sukunimi + ", sahkoposti=" + sahkoposti
                + ", salasana=" + salasana + ", rooli=" + rooli + "]";
    }

}
