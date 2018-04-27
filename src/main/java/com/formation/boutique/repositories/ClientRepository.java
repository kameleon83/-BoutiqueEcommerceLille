package com.formation.boutique.repositories;

import com.formation.boutique.entities.Client;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, String> {

    Client findClientByEmail(String email);

    Client findClientByEmailAndPassword(String email, String password);

}
