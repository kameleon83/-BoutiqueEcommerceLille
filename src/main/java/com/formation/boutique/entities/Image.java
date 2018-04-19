package com.formation.boutique.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Image {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String lien;
}
