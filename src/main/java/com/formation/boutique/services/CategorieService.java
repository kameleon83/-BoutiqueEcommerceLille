package com.formation.boutique.services;

import com.formation.boutique.entities.Categorie;
import com.formation.boutique.repositories.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CategorieService {

    private final CategorieRepository categorieRepository;

    @Autowired
    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    public Iterable<Categorie> getAll(){
        return categorieRepository.findAll();
    }

    public Categorie save(Categorie categorie){
        return categorieRepository.save(categorie);
    }

}
