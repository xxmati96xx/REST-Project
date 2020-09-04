package com.exemple.REST.Project.dao;

import com.exemple.REST.Project.model.Client;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository("testdao")
public class TestClientDataAccessService implements ClientDao{

    private static List<Client> DB = new ArrayList<>();

    @Override
    public int insertClient(UUID id, Client client) {
        DB.add(new Client(id,client.getFname(),client.getLname(),client.getAddress()));
        return 1;
    }

    @Override
    public List<Client> selectAllClient() {
        return DB;
    }

    @Override
    public Optional<Client> selectClientById(UUID id) {
        return DB.stream()
                .filter(client -> client.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deleteClientById(UUID id) {
        Optional<Client> clientExist = selectClientById(id);
        if(clientExist.isEmpty()){
            return 0;
        }
        DB.remove(clientExist.get());
        return 1;
    }

    @Override
    public int updateClientById(UUID id, Client client) {
        return selectClientById(id)
                .map(c -> {
                    int indexOfClientToUpdate = DB.indexOf(c);
                    if(indexOfClientToUpdate >= 0){
                        DB.set(indexOfClientToUpdate, new Client(id,client.getFname(),client.getLname(),client.getAddress()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }


}
