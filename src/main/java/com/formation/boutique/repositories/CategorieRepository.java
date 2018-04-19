package com.formation.boutique.repositories;

import com.formation.boutique.entities.Categorie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CategorieRepository extends CrudRepository<Categorie, Long> {

    Collection<Categorie> findByNom(String nom);
}
