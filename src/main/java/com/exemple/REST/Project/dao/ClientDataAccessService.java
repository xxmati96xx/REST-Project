package com.exemple.REST.Project.dao;

import com.exemple.REST.Project.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
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
        final String sql = "INSERT INTO client (id,fname,lname,address) VALUES (?,?,?,?)";
        return jdbcTemplate.update(sql,id,client.getFname(),client.getLname(),client.getAddress());

    }

    @Override
    public List<Client> selectAllClient() {
        final String sql = "SELECT id,fname,lname,address from client";
        return jdbcTemplate.query(sql,(resultSet,i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String fname = resultSet.getString("fname");
            String lname = resultSet.getString("lname");
            String address = resultSet.getString("address");
            return new Client(id,fname,lname,address);
        });
    }

    @Override
    public Optional<Client> selectClientById(UUID id) {
        final String sql = "SELECT id,fname,lname,address from client WHERE id= ?";
        Client client = jdbcTemplate.queryForObject(sql,new Object[]{id},(resultSet,i) -> {
            UUID clientId = UUID.fromString(resultSet.getString("id"));
            String fname = resultSet.getString("fname");
            String lname = resultSet.getString("lname");
            String address = resultSet.getString("address");
            return new Client(clientId,fname,lname,address);
        });
        return Optional.ofNullable(client);
    }

    @Override
    public int deleteClientById(UUID id) {
        final String sql = "DELETE FROM client WHERE id= ?";
        return jdbcTemplate.update(sql,id);
    }

    @Override
    public int updateClientById(UUID id, Client client) { //naprawić update
        final String sql = "INSERT INTO client (id,fname,lname,address) VALUES (?,?,?,?)";
        return jdbcTemplate.update(sql,id,client.getFname(),client.getLname(),client.getAddress());
    }
}
