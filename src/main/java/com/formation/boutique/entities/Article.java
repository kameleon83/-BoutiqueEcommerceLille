package com.formation.boutique.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;
    @NotNull
    @NotBlank
    private String nom;
    @NotNull
    @Positive
    private Float prix;
    @Column(columnDefinition = "TEXT")
    private String description;
    @PositiveOrZero
    private Integer nbVentes;
    @PositiveOrZero
    private Integer promo;

    @OneToMany(mappedBy = "article")
    private Collection<Image> images;

    @ManyToOne
    @NotNull
    private Categorie categorie;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "commande",
//            joinColumns = { @JoinColumn(name = "code") },
//            inverseJoinColumns = { @JoinColumn(name = "email") })
    @OneToMany(mappedBy = "article")
    private Collection<Commande> commandes = new ArrayList<>();

    public Article() {
    }

    public Article(Long code, @NotNull String nom, @NotNull @Positive Float prix, String description, @Positive Integer nbVentes, @Positive @Size(min = 1, max = 2) Integer promo, Collection<Image> images, Categorie categorie, Collection<Commande> commandes) {
        this.code = code;
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.nbVentes = nbVentes;
        this.promo = promo;
        this.images = images;
        this.categorie = categorie;
        this.commandes = commandes;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNbVentes() {
        return nbVentes;
    }

    public void setNbVentes(Integer nbVentes) {
        this.nbVentes = nbVentes;
    }

    public Integer getPromo() {
        return promo;
    }

    public void setPromo(Integer promo) {
        this.promo = promo;
    }

    public Collection<Image> getImages() {
        return images;
    }

    public void setImages(Collection<Image> images) {
        this.images = images;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Collection<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(Collection<Commande> commandes) {
        this.commandes = commandes;
    }

    @Override
    public String toString() {
        return "Article{" +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", description='" + description + '\'' +
                ", nbVentes=" + nbVentes +
                ", promo=" + promo +
                ", images=" + images +
                ", categorie=" + categorie +
                ", commandes=" + commandes +
                '}';
    }
}
