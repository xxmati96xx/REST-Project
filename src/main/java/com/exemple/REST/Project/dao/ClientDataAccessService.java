package com.exemple.REST.Project.dao;

import com.exemple.REST.Project.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class ClientDataAccessService implements ClientDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClientDataAccessService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertClient(UUID id, Client client) {
        return 0;
    }

    @Override
    public List<Client> selectAllClient() {
        return null;
    }

    @Override
    public Optional<Client> selectClientById(UUID id) {
        return Optional.empty();
    }

    @Override
    public int deleteClientById(UUID id) {
        return 0;
    }

    @Override
    public int updateClientById(UUID id, Client client) {
        return 0;
    }
}
