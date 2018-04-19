package com.formation.boutique.entities;



import com.formation.boutique.enumerations.Civilite;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
}
