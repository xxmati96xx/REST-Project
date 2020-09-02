package com.exemple.REST.Project.dao;

import com.exemple.REST.Project.model.Client;

import java.util.List;
import java.util.UUID;

public interface ClientDao {

    int insertClient(UUID id, Client client);

    default int insertClient(Client client){
        UUID id = UUID.randomUUID();
        return insertClient(id,client);
    }

    List<Client> selectAllClient();
}
