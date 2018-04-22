package com.formation.boutique.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.formation.boutique.enumerations.Civilite;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Client {
    @Id
    @Column(length = 150)
    private String email;
    @NotNull
    @Column(length = 150)
    private String nom;
    @NotNull
    private String prenom;
    @NotNull
    private String password;
    @NotNull
    @Size(max = 5)
    private Integer numAdresse;
    @NotNull
    private String rueAdresse;
    @NotNull
    @Size(max = 5, min = 5)
    private Integer cpAdresse;
    @NotNull
    @Column(length = 50)
    @Size(min = 1, max = 50)
    private String villeAdresse;
    @Column(length = 100)
    @Size(max = 100)
    private String compAdresse;
    @NotNull
    @DateTimeFormat
    private Date dateNaissance;
    @NotNull
    @Column(length = 10)
    @Size(min = 10, max = 10)
    private String tel;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Civilite civilite;

    //    @ManyToMany(cascade = CascadeType.ALL)
    //    @JoinTable(name = "commande",
    //            joinColumns = { @JoinColumn(name = "email") },
    //            inverseJoinColumns = { @JoinColumn(name = "code") })
    @OneToMany(mappedBy = "client")
    private Collection<Commande> commandes = new ArrayList<>();

    public Client() {
    }

    public Client(String email, @NotNull String nom, @NotNull String prenom, @NotNull String password,
            @NotNull @Size(max = 5) Integer numAdresse, @NotNull String rueAdresse,
            @NotNull @Size(max = 5, min = 5) Integer cpAdresse, @NotNull @Size(min = 1, max = 50) String villeAdresse,
            @Size(max = 100) String compAdresse, @NotNull Date dateNaissance,
            @NotNull @Size(min = 10, max = 10) String tel, @NotNull Civilite civilite, Collection<Commande> commandes) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.numAdresse = numAdresse;
        this.rueAdresse = rueAdresse;
        this.cpAdresse = cpAdresse;
        this.villeAdresse = villeAdresse;
        this.compAdresse = compAdresse;
        this.dateNaissance = dateNaissance;
        this.tel = tel;
        this.civilite = civilite;
        this.commandes = commandes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getNumAdresse() {
        return numAdresse;
    }

    public void setNumAdresse(Integer numAdresse) {
        this.numAdresse = numAdresse;
    }

    public String getRueAdresse() {
        return rueAdresse;
    }

    public void setRueAdresse(String rueAdresse) {
        this.rueAdresse = rueAdresse;
    }

    public Integer getCpAdresse() {
        return cpAdresse;
    }

    public void setCpAdresse(Integer cpAdresse) {
        this.cpAdresse = cpAdresse;
    }

    public String getVilleAdresse() {
        return villeAdresse;
    }

    public void setVilleAdresse(String villeAdresse) {
        this.villeAdresse = villeAdresse;
    }

    public String getCompAdresse() {
        return compAdresse;
    }

    public void setCompAdresse(String compAdresse) {
        this.compAdresse = compAdresse;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Civilite getCivilite() {
        return civilite;
    }

    public void setCivilite(Civilite civilite) {
        this.civilite = civilite;
    }

    public Collection<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(Collection<Commande> commandes) {
        this.commandes = commandes;
    }
}
