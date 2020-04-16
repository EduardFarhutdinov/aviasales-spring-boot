package com.aviasales.repositories;

import com.aviasales.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

    public Client findClientById(Long clientId);
}
