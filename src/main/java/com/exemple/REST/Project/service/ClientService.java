package com.exemple.REST.Project.service;

import com.exemple.REST.Project.Entity.ClientEntity;
import com.exemple.REST.Project.dao.ClientDao;
import com.exemple.REST.Project.dao.ClientRepository;
import com.exemple.REST.Project.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    private final ClientDao clientDao;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    public ClientService(@Qualifier("postgres") ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public ClientEntity addClient(ClientEntity client){
        return clientRepository.save(client);
    }

    public Page<ClientEntity> getAllClient(Pageable pageable){
        return clientRepository.findAll(pageable);
    }

    public Optional<ClientEntity> getClientById(UUID id){
        return clientRepository.findById(id);
    }

    public String deleteClientById(UUID id){
        clientRepository.deleteById(id);
        return "Remove client: "+ id;
    }

    public int updateClientById(UUID id,Client newClient){
        return clientDao.updateClientById(id,newClient);
    }
}
