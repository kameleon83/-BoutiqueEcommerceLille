package com.formation.boutique.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Categorie {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(length = 100)
    private String nom;

    @OneToOne
    private Categorie categorie;

    @OneToMany(mappedBy = "categorie")
    private Collection<Article> articles = new ArrayList<>();

    public Categorie() {
    }

    public Categorie(@NotNull String nom) {
        this.nom = nom;
    }

    public Categorie(@NotNull String nom, Categorie categorie, Collection<Article> articles) {
        this.nom = nom;
        this.categorie = categorie;
        this.articles = articles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Collection<Article> getArticles() {
        return articles;
    }

    public void setArticles(Collection<Article> articles) {
        this.articles = articles;
    }
}
