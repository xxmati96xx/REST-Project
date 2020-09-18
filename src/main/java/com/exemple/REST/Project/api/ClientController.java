package com.exemple.REST.Project.api;

import com.exemple.REST.Project.Entity.ClientEntity;
import com.exemple.REST.Project.assemblers.ClientModelAssembler;
import com.exemple.REST.Project.model.Client;
import com.exemple.REST.Project.model.ClientModel;
import com.exemple.REST.Project.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequestMapping("api/v1/clients")
@RestController
public class ClientController{
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    private PagedResourcesAssembler<ClientEntity> pagedResourcesAssembler;

    @Autowired
    private ClientModelAssembler clientModelAssembler;
    @PostMapping
    public ResponseEntity<EntityModel<ClientEntity>> addClient(@RequestBody ClientEntity client)
    {
        ClientEntity clientEntity = clientService.addClient(client);
        Link link = linkTo(ClientController.class).slash(clientEntity.getId()).withSelfRel();
        Link linkAll = linkTo(ClientController.class).withRel("All clients");
        EntityModel<ClientEntity> clientEntityModel = EntityModel.of(clientEntity,link,linkAll);
        return new ResponseEntity<>(clientEntityModel,HttpStatus.CREATED);
    }


    @GetMapping(path = "{id}")
    public ResponseEntity<EntityModel<ClientEntity>> getClientById(@PathVariable("id") UUID id){
        Link link = linkTo(ClientController.class).slash(id).withSelfRel();
        Link linkAll = linkTo(ClientController.class).withRel("All clients");
        EntityModel<ClientEntity> clientEntityModel = EntityModel.of(clientService.getClientById(id)
                .orElse(null),link,linkAll);
        return new ResponseEntity<>(clientEntityModel, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PagedModel<ClientModel>> getAllPeople(Pageable pageable){
        Page<ClientEntity> allClient = clientService.getAllClient(pageable);
        PagedModel<ClientModel> clientCollectionModel = pagedResourcesAssembler.toModel(allClient,clientModelAssembler);
        return new ResponseEntity<>(clientCollectionModel,HttpStatus.OK);
    }

    /*@GetMapping(path = "{id}")
    public ResponseEntity<EntityModel<Client>> getClientById(@PathVariable("id") UUID id){
        Link link = linkTo(ClientController.class).slash(id).withSelfRel();
        EntityModel<Client> clientEntityModel = EntityModel.of(clientService.getClientById(id)
                .orElse(null),link);
        return new ResponseEntity<>(clientEntityModel, HttpStatus.OK);
    }*/

    @DeleteMapping(path = "{id}")
    public ResponseEntity<ClientEntity> deleteClientById(@PathVariable("id") UUID id) {
        return clientService.deleteClientById(id);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<EntityModel<ClientEntity>> updateClientById(@PathVariable("id") UUID id,@Valid @RequestBody ClientEntity clientToUpdate){
       return clientService.updateClientById(id, clientToUpdate);
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity<ClientEntity> updatePartialClientById(@PathVariable("id") UUID id, @RequestBody Client clientToUpdate){
        return clientService.updatePartialClientById(id, clientToUpdate);
    }
}
