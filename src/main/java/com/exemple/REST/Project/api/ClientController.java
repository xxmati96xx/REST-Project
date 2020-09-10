package com.exemple.REST.Project.api;

import com.exemple.REST.Project.model.Client;
import com.exemple.REST.Project.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequestMapping("api/v1/client")
@RestController
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public void addClient(@Valid @RequestBody Client client){
        clientService.addClient(client);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Client>> getAllPeople(){
        List<Client> allClient = clientService.getAllClient();
        allClient.forEach(client -> client.add(linkTo(ClientController.class).slash(client.getId()).withSelfRel()).add(linkTo(ClientController.class).withRel("All clients")));
        CollectionModel<Client> clientCollectionModel = CollectionModel.of(allClient,linkTo(ClientController.class).withSelfRel());
        return new ResponseEntity<>(clientCollectionModel,HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<EntityModel<Client>> getClientById(@PathVariable("id") UUID id){
        Link link = linkTo(ClientController.class).slash(id).withSelfRel();
        EntityModel<Client> clientEntityModel = EntityModel.of(clientService.getClientById(id)
                .orElse(null),link);
        return new ResponseEntity<>(clientEntityModel, HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public void deleteClientById(@PathVariable("id") UUID id) {
        clientService.deleteClientById(id);
    }

    @PutMapping(path = "{id}")
    public void updateClientById(@PathVariable("id") UUID id,@Valid @RequestBody Client clientToUpdate){
        clientService.updateClientById(id, clientToUpdate);
    }

    //@PatchMapping(path = "{id}")
    //public ResponseEntity<void>
}
