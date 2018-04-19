package com.formation.boutique.services;

import com.formation.boutique.entities.Client;
import com.formation.boutique.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Iterable<Client> getAll(){
        return clientRepository.findAll();
    }

    public Client save(Client Client){
        return clientRepository.save(Client);
    }

}
