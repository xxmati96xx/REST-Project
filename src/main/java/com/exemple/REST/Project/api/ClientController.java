package com.exemple.REST.Project.api;

import com.exemple.REST.Project.model.Client;
import com.exemple.REST.Project.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

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
    public List<Client> getAllPeople(){
        return clientService.getAllClient();
    }

    @GetMapping(path = "{id}")
    public Client getClientById(@PathVariable("id") UUID id){
        return clientService.getClientById(id)
                .orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deleteClientById(@PathVariable("id") UUID id) {
        clientService.deleteClientById(id);
    }

    @PutMapping(path = "{id}")
    public void updateClientById(@PathVariable("id") UUID id,@Valid @RequestBody Client clientToUpdate){
        clientService.updateClientById(id, clientToUpdate);
    }
}
