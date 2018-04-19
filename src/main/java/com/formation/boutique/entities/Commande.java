package com.formation.boutique.entities;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "commande")
public class Commande {

    @Id
    @NotNull
    private Long code;
    @DateTimeFormat
    private Date date;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Article article;

    public Commande() {
    }

    public Commande(@NotNull Long code, Date date, Client client, Article article) {
        this.code = code;
        this.date = date;
        this.client = client;
        this.article = article;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
