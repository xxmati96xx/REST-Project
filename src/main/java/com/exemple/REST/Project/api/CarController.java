package com.exemple.REST.Project.api;

import com.exemple.REST.Project.Entity.CarEntity;
import com.exemple.REST.Project.Entity.ClientEntity;
import com.exemple.REST.Project.assemblers.CarModelAssembler;
import com.exemple.REST.Project.assemblers.ClientModelAssembler;
import com.exemple.REST.Project.model.CarModel;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequestMapping("api/v1/cars")
@RestController
public class CarController{
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @Autowired
    private PagedResourcesAssembler<CarEntity> pagedResourcesAssembler;

    @Autowired
    private CarModelAssembler carModelAssembler;

    @PostMapping
    public ResponseEntity<Link> addCar()
    {
        return carService.addCar();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<EntityModel<CarEntity>> getClientById(@PathVariable("id") UUID id){
        return carService.getCarById(id);
    }

    @GetMapping
    public ResponseEntity<PagedModel<CarModel>> getAllPeople(Pageable pageable){
        Page<CarEntity> allCar = carService.getAllCar(pageable);
        PagedModel<CarModel> carCollectionModel = pagedResourcesAssembler.toModel(allCar,carModelAssembler);
        return new ResponseEntity<>(carCollectionModel,HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<CarEntity> deleteCarById(@PathVariable("id") UUID id,@RequestBody CarEntity carEntity) {
        return carService.deleteCarById(id,carEntity);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<EntityModel<CarEntity>> updateCarById(@PathVariable("id") UUID id,@Valid @RequestBody CarEntity carToUpdate){
        return carService.updateCarById(id, carToUpdate);
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity<CarEntity> updatePartialCarById(@PathVariable("id") UUID id, @RequestBody CarEntity carToUpdate){
        return carService.updatePartialCarById(id, carToUpdate);
    }


}
