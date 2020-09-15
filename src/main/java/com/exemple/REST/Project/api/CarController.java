package com.exemple.REST.Project.api;

import com.exemple.REST.Project.Entity.CarEntity;
import com.exemple.REST.Project.Entity.ClientEntity;
import com.exemple.REST.Project.assemblers.ClientModelAssembler;
import com.exemple.REST.Project.model.Client;
import com.exemple.REST.Project.model.ClientModel;
import com.exemple.REST.Project.service.CarService;
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

@RequestMapping("api/v1/car")
@RestController
public class CarController{
    private final CarService carService;

    //@Autowired
    //private ClientRepository albumRepository;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @Autowired
    private PagedResourcesAssembler<CarEntity> pagedResourcesAssembler;

    @Autowired
    private ClientModelAssembler clientModelAssembler;
    /*
    @PostMapping
    public ResponseEntity<EntityModel<CarEntity>> addClient(@RequestBody CarEntity car)
    {
        CarEntity carEntity = carService.addCar(car);
        Link link = linkTo(CarController.class).slash(carEntity.getId()).withSelfRel();
        Link linkAll = linkTo(CarController.class).withRel("All cars");
        EntityModel<CarEntity> clientEntityModel = EntityModel.of(carEntity,link,linkAll);
        return new ResponseEntity<>(clientEntityModel,HttpStatus.CREATED);
    }

     */

    @PostMapping
    public ResponseEntity<Link> addClient()
    {
        Link link = linkTo(CarController.class).slash(carService.getUUID()).withSelfRel();
        System.out.println();
        return new ResponseEntity<>(link,HttpStatus.CREATED);
    }


    @GetMapping(path = "{id}")
    public ResponseEntity<EntityModel<CarEntity>> getClientById(@PathVariable("id") UUID id){
        Link link = linkTo(CarController.class).slash(id).withSelfRel();
        Link linkAll = linkTo(CarController.class).withRel("All car");
        EntityModel<CarEntity> carEntityModel = EntityModel.of(carService.getCarById(id)
                .orElse(null),link,linkAll);
        return new ResponseEntity<>(carEntityModel, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<CarEntity>> getAllPeople(Pageable pageable){
        Page<CarEntity> allCar = carService.getAllCar(pageable);
        //PagedModel<ClientModel> clientCollectionModel = pagedResourcesAssembler.toModel(allClient,clientModelAssembler);
        return new ResponseEntity<>(allCar,HttpStatus.OK);
    }

    /*@GetMapping(path = "{id}")
    public ResponseEntity<EntityModel<Client>> getClientById(@PathVariable("id") UUID id){
        Link link = linkTo(ClientController.class).slash(id).withSelfRel();
        EntityModel<Client> clientEntityModel = EntityModel.of(clientService.getClientById(id)
                .orElse(null),link);
        return new ResponseEntity<>(clientEntityModel, HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public String deleteClientById(@PathVariable("id") UUID id) {
        return clientService.deleteClientById(id);
    }
*/
    @PutMapping(path = "{id}")
    public ResponseEntity<CarEntity> updateCarById(@PathVariable("id") UUID id,@Valid @RequestBody CarEntity carToUpdate){
        return new ResponseEntity<>(carService.updateCarById(id, carToUpdate),HttpStatus.NO_CONTENT);
    }
/*
    @PatchMapping(path = "{id}")
    public int updatePartialClientById(@PathVariable("id") UUID id, @RequestBody Client clientToUpdate){
        return clientService.updatePartialClientById(id, clientToUpdate);
    }
    */

}
