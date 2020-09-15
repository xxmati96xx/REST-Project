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
import org.springframework.util.StringUtils;

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

    public ClientEntity updateClientById(UUID id,Client newClient){
        ClientEntity client = clientRepository.findById(id).orElse(new ClientEntity());
        if(client.getId() == null) {
            client.setId(id);
        }
            if(     !StringUtils.isEmpty(newClient.getFname()) &&
                    !StringUtils.isEmpty(newClient.getLname()) &&
                    !StringUtils.isEmpty(newClient.getAddress())){
                client.setFname(newClient.getFname());
                client.setLname(newClient.getLname());
                client.setAddress(newClient.getAddress());
                clientRepository.save(client);
                return client;
            }
        return null;
        }
    public int updatePartialClientById(UUID id,Client newClient){
        ClientEntity client = clientRepository.findById(id).orElse(null);
        if(client.getId() != null) {
            /*
            if(newClient.getFname()!= null) {
                client.setFname(newClient.getFname());
            }
            if(newClient.getLname() != null) {
                client.setLname(newClient.getLname());
            }
            if(newClient.getAddress()!=null) {
                client.setAddress(newClient.getAddress());
            }
            */
            Optional.ofNullable(newClient.getFname())
                    .filter(fname -> !StringUtils.isEmpty(fname))
                    .map(StringUtils::capitalize)
                    .ifPresent(fname -> client.setFname(newClient.getFname()));
            Optional.ofNullable(newClient.getLname())
                    .filter(lname -> !StringUtils.isEmpty(lname))
                    .map(StringUtils::capitalize)
                    .ifPresent(lirstName -> client.setLname(newClient.getLname()));
            Optional.ofNullable(newClient.getAddress())
                    .filter(address -> !StringUtils.isEmpty(address))
                    .ifPresent(address -> client.setAddress(newClient.getAddress()));
            clientRepository.save(client);
            return 1;
        }
        return 0;
    }
}

