package com.drinkkiluostari.backend.domain;

import com.drinkkiluostari.backend.dto.RooliDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "roolit")
public class Rooli {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 100)
    private String nimi;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rooli")
    private List<Tyontekija> tyontekijat;

    public Rooli() {
    }

    public Rooli(String nimi) {
        this.nimi = nimi;
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
    
    public List<Tyontekija> getTyontekijat() {
        return tyontekijat;
    }

    public void setTyontekijat(List<Tyontekija> tyontekijat) {
        this.tyontekijat = tyontekijat;
    }

    public RooliDTO toDTO() {
        return new RooliDTO(
            this.id,
            this.nimi,
            
            this.tyontekijat.stream()
                .map(Tyontekija::getId)
                .collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return "Rooli [id=" + id + ", nimi=" + nimi + "]";
    }

}
