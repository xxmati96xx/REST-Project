package com.exemple.REST.Project.service;

import com.exemple.REST.Project.dao.ClientDao;
import com.exemple.REST.Project.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    private final ClientDao clientDao;

    @Autowired
    public ClientService(@Qualifier("postgres") ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public int addClient(Client client){
        return clientDao.insertClient(client);
    }

    public List<Client> getAllClient(){
        return clientDao.selectAllClient();
    }

    public Optional<Client> getClientById(UUID id){
        return clientDao.selectClientById(id);
    }

    public int deleteClientById(UUID id){
        return clientDao.deleteClientById(id);
    }

    public int updateClientById(UUID id,Client newClient){
        return clientDao.updateClientById(id,newClient);
    }
}
