package com.formation.boutique.repositories;

import com.formation.boutique.entities.Article;
import com.formation.boutique.entities.Commande;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepository extends CrudRepository<Commande, Long> {
}
