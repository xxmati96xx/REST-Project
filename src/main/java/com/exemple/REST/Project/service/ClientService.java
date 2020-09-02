package com.exemple.REST.Project.service;

import com.exemple.REST.Project.dao.ClientDao;
import com.exemple.REST.Project.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientDao clientDao;

    @Autowired
    public ClientService(@Qualifier("testDao") ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public int addClient(Client client){
        return clientDao.insertClient(client);
    }

    public List<Client> getAllClient(){
        return clientDao.selectAllClient();
    }
}
