package com.formation.boutique.entities;

import com.formation.boutique.enumerations.Civilite;
import com.formation.boutique.enumerations.Droit;
import com.formation.boutique.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
    @NotBlank
//    @Column(updatable = false)
    private String password;
    @Transient
    private String passwordVerif;
    @NotNull
    private Integer numAdresse;
    @NotNull
    private String rueAdresse;
    @NotNull
    private Integer cpAdresse;
    @NotNull
    @Column(length = 50)
    @Size(min = 1, max = 50)
    private String villeAdresse;
    @Column(length = 100)
    @Size(max = 100)
    private String compAdresse;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "DATE")
    private Date dateNaissance;
    @NotNull
    @Column(length = 10)
    @Size(min = 10, max = 10)
    private String tel;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Civilite civilite;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Droit droit;

    //    @ManyToMany(cascade = CascadeType.ALL)
    //    @JoinTable(name = "commande",
    //            joinColumns = { @JoinColumn(name = "email") },
    //            inverseJoinColumns = { @JoinColumn(name = "code") })
    @OneToMany(mappedBy = "client")
    private Collection<Commande> commandes = new ArrayList<>();

    @Transient
    private String changePassword;


    public Client() {
    }

    public Client(String email, @NotNull String nom, @NotNull String prenom, @NotNull String password, String passwordVerif, @NotNull @Size(max = 5) Integer numAdresse, @NotNull String rueAdresse, @NotNull @Size(max = 5, min = 5) Integer cpAdresse, @NotNull @Size(min = 1, max = 50) String villeAdresse, @Size(max = 100) String compAdresse, @NotNull Date dateNaissance, @NotNull @Size(min = 10, max = 10) String tel, @NotNull Civilite civilite, @NotNull Droit droit, Collection<Commande> commandes) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.passwordVerif = passwordVerif;
        this.numAdresse = numAdresse;
        this.rueAdresse = rueAdresse;
        this.cpAdresse = cpAdresse;
        this.villeAdresse = villeAdresse;
        this.compAdresse = compAdresse;
        this.dateNaissance = dateNaissance;
        this.tel = tel;
        this.civilite = civilite;
        this.droit = droit;
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
        if (password == null) {
            this.password = this.changePassword;
        } else {
            this.password = get_SHA_512_SecurePassword(password);

        }
    }

    public String getPasswordVerif() {
        return passwordVerif;
    }

    public void setPasswordVerif(String passwordVerif) {
        if (passwordVerif == null) {
            this.passwordVerif = this.changePassword;
        } else {
            this.passwordVerif = get_SHA_512_SecurePassword(passwordVerif);
        }
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

    public Droit getDroit() {
        return droit;
    }

    public void setDroit(Droit droit) {
        this.droit = droit;
    }

    public String getChangePassword() {
        return changePassword;
    }

    public void setChangePassword(String changePassword) {
        this.changePassword = changePassword;
    }

    public static String get_SHA_512_SecurePassword(String passwordToHash) {
        String generatedPassword = null;
        String salt = "olprog";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    @Override
    public String toString() {
        return "Client{" +
                "email='" + email + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", password='" + password + '\'' +
                ", passwordVerif='" + passwordVerif + '\'' +
                ", numAdresse=" + numAdresse +
                ", rueAdresse='" + rueAdresse + '\'' +
                ", cpAdresse=" + cpAdresse +
                ", villeAdresse='" + villeAdresse + '\'' +
                ", compAdresse='" + compAdresse + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", tel='" + tel + '\'' +
                ", civilite=" + civilite +
                ", droit=" + droit +
                ", commandes=" + commandes +
                '}';
    }
}
