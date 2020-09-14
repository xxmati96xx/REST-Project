package com.exemple.REST.Project.dao;

import com.exemple.REST.Project.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientDao {

    int insertClient(UUID id, Client client);

    default int insertClient(Client client){
        UUID id = UUID.randomUUID();
        return insertClient(id,client);
    }

    List<Client> selectAllClient();

    Optional<Client> selectClientById(UUID id);

    int deleteClientById(UUID id);

    int updateClientById(UUID id,Client client);
}
