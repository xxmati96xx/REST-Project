package com.exemple.REST.Project.service;

import com.exemple.REST.Project.Entity.ClientEntity;
import com.exemple.REST.Project.api.CarController;
import com.exemple.REST.Project.dao.ClientDao;
import com.exemple.REST.Project.dao.ClientRepository;
import com.exemple.REST.Project.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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
        client.setId(getUUID());
        return clientRepository.save(client);
    }

    public Page<ClientEntity> getAllClient(Pageable pageable){
        return clientRepository.findAll(pageable);
    }

    public Optional<ClientEntity> getClientById(UUID id){
        return clientRepository.findById(id);
    }

    public ResponseEntity<ClientEntity> deleteClientById(UUID id){
        ClientEntity client = clientRepository.findById(id).orElse(null);
        if(client != null) {
            clientRepository.deleteById(id);
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    public UUID getUUID(){
        UUID tmpId = UUID.randomUUID();
        return tmpId;
    }

    public ResponseEntity<EntityModel<ClientEntity>> updateClientById(UUID id, ClientEntity newClient){
        ClientEntity client = clientRepository.findById(id).orElse(newClient);
        if(client.getId() == null &&
                !StringUtils.isEmpty(newClient.getFname()) &&
                !StringUtils.isEmpty(newClient.getLname()) &&
                !StringUtils.isEmpty(newClient.getAddress())) {
            client.setId(id);
            Link link = linkTo(CarController.class).slash(client.getId()).withSelfRel();
            Link linkAll = linkTo(CarController.class).withRel("All clients");
            EntityModel<ClientEntity> clientEntityEntityModel = EntityModel.of(clientRepository.save(client),link,linkAll);
            return new ResponseEntity<>(clientEntityEntityModel, HttpStatus.CREATED);
        }else if(!StringUtils.isEmpty(newClient.getFname()) &&
                !StringUtils.isEmpty(newClient.getLname()) &&
                !StringUtils.isEmpty(newClient.getAddress()))
            {
                client.setFname(newClient.getFname());
                client.setLname(newClient.getLname());
                client.setAddress(newClient.getAddress());
                EntityModel<ClientEntity> entityEntityModel = EntityModel.of(clientRepository.save(client));
                return new ResponseEntity<>(entityEntityModel, HttpStatus.NO_CONTENT);
            }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    public ResponseEntity<ClientEntity> updatePartialClientById(UUID id,Client newClient){
        ClientEntity client = clientRepository.findById(id).orElse(null);
        if(client != null) {
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
            return new ResponseEntity<>(clientRepository.save(client), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
}

