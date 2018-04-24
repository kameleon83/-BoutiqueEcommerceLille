package com.formation.boutique.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String lien;

    @ManyToOne
    private Article article;

    public Image() {
    }

    public Image(@NotNull String lien, Article article) {
        this.lien = lien;
        this.article = article;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public String toString() {
        return "Image{" +
                ", lien='" + lien + '\'' +
//                ", article=" + article +
                '}';
    }
}
