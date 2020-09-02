package com.exemple.REST.Project.api;

import com.exemple.REST.Project.model.Client;
import com.exemple.REST.Project.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/client")
@RestController
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public void addClient(@RequestBody Client client){
        clientService.addClient(client);
    }

    @GetMapping
    public List<Client> getAllPeople(){
        return clientService.getAllClient();
    }
}
