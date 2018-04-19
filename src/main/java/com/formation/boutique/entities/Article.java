package com.formation.boutique.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
public class Article {
    @Id
    private Long code;
    @NotNull
    private String nom;
    @NotNull
    @Positive
    private Float prix;
    @Column(columnDefinition = "text")
    private String description;
    @Positive
    private Integer nbVentes;
    @Positive
    @Column(length = 2)
    @Size(min = 1, max = 2)
    private Integer promo;

}
