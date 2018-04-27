package com.formation.boutique.services;

import com.formation.boutique.entities.Categorie;
import com.formation.boutique.entities.Client;
import com.formation.boutique.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

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

    public Client getOne(String email){return clientRepository.findClientByEmail(email);}

    public Boolean getUserCount(){
        return clientRepository.count() > 0;
    }

    public void delete(Client client){
        clientRepository.delete(client);
    }

    public Client login(String email, String password){
        return clientRepository.findClientByEmailAndPassword(email, password);
    }

}
