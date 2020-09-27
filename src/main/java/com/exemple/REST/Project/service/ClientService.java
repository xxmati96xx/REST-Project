package com.exemple.REST.Project.service;

import com.exemple.REST.Project.Entity.CarEntity;
import com.exemple.REST.Project.Entity.CheckoutEntity;
import com.exemple.REST.Project.Entity.ClientEntity;
import com.exemple.REST.Project.api.CarController;
import com.exemple.REST.Project.api.ClientController;
import com.exemple.REST.Project.dao.CheckoutRepository;
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

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CheckoutRepository checkoutRepository;

    public ResponseEntity<EntityModel<ClientEntity>> addClient(ClientEntity client){
        if(     !StringUtils.isEmpty(client.getFname()) &&
                !StringUtils.isEmpty(client.getLname()) &&
                !StringUtils.isEmpty(client.getAddress())) {
            client.setId(getUUID());
            client.setVersion(0L);
            clientRepository.save(client);
            Link link = linkTo(ClientController.class).slash(client.getId()).withSelfRel();
            Link linkAll = linkTo(ClientController.class).withRel("All clients");
            EntityModel<ClientEntity> clientEntityModel = EntityModel.of(client, link, linkAll);
            return new ResponseEntity<>(clientEntityModel,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }

    public Page<ClientEntity> getAllClient(Pageable pageable){
        return clientRepository.findAll(pageable);
    }

    public ResponseEntity<EntityModel<ClientEntity>> getClientById(UUID id){
        Link link = linkTo(ClientController.class).slash(id).withSelfRel();
        Link linkAll = linkTo(ClientController.class).withRel("All clients");
        ClientEntity clientEntity = clientRepository.findById(id).orElse(null);
        if (clientEntity != null) {
            EntityModel<ClientEntity> entityModel = EntityModel.of(clientEntity, link, linkAll);
            return new ResponseEntity<>(entityModel, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<ClientEntity> deleteClientById(UUID id, ClientEntity clientEntity){
        List<CheckoutEntity> checkoutEntities = checkoutRepository.findAllByClient_Id(id);
        ClientEntity client = clientRepository.findById(id).orElse(null);
        if(client != null) {
            if(checkoutEntities.isEmpty() && client.getVersion().equals(clientEntity.getVersion())) {
                clientRepository.deleteById(id);
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    public UUID getUUID(){
        return UUID.randomUUID();
    }

    public ResponseEntity<EntityModel<ClientEntity>> updateClientById(UUID id, ClientEntity newClient){
        ClientEntity client = clientRepository.findById(id).orElse(newClient);
        if(client.getId() == null &&
                !StringUtils.isEmpty(newClient.getFname()) &&
                !StringUtils.isEmpty(newClient.getLname()) &&
                !StringUtils.isEmpty(newClient.getAddress())) {
            client.setId(id);
            client.setVersion(0L);
            Link link = linkTo(CarController.class).slash(client.getId()).withSelfRel();
            Link linkAll = linkTo(CarController.class).withRel("All clients");
            EntityModel<ClientEntity> clientEntityEntityModel = EntityModel.of(clientRepository.save(client),link,linkAll);
            return new ResponseEntity<>(clientEntityEntityModel, HttpStatus.CREATED);
        }else if(client.getVersion().equals(newClient.getVersion()) &&
                !StringUtils.isEmpty(newClient.getFname()) &&
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

