package com.aviasales.service;

import com.aviasales.model.Client;
import com.aviasales.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client findClientById(Long id){
        return clientRepository.findClientById(id);
    }

    public void saveClient(Client client){
        clientRepository.save(client);
    }
}
