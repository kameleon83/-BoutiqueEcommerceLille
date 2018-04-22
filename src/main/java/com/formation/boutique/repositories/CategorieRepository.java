package com.formation.boutique.repositories;

import com.formation.boutique.entities.Categorie;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface CategorieRepository extends CrudRepository<Categorie, Long> {

    Collection<Categorie> findByNom(String nom);

    Collection<Categorie> findAllByOrderByArticlesAsc();

    Collection<Categorie> findCategoriesByParentOrderByNom(Categorie categorie);
}
