package com.formation.boutique.services;

import com.formation.boutique.entities.Commande;
import com.formation.boutique.repositories.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandeService {

    private final CommandeRepository commandeRepository;

    @Autowired
    public CommandeService(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    public Iterable<Commande> getAll(){
        return commandeRepository.findAll();
    }

    public Commande save(Commande Commande){
        return commandeRepository.save(Commande);
    }

}
