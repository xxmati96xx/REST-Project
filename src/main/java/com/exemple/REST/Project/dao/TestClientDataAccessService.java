package com.exemple.REST.Project.dao;

import com.exemple.REST.Project.model.Client;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Repository("testDao")
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


}
