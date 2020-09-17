package com.exemple.REST.Project.api;


import com.exemple.REST.Project.Entity.*;
import com.exemple.REST.Project.model.ClientCarOneModel;
import com.exemple.REST.Project.assemblers.ClientCarModelAssembler;
import com.exemple.REST.Project.model.ClientCarModel;
import com.exemple.REST.Project.service.ReservationService;
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
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequestMapping("api/v1/reservation")
@RestController
public class ReservationController {


    private final ReservationService reservationService;

    @Autowired
    private PagedResourcesAssembler<ClientCarEntity> pagedResourcesAssembler;

    @Autowired
    private ClientCarModelAssembler clientCarModelAssembler;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Link> addReservation()
    {
       return reservationService.addReservation();
    }

    @GetMapping
    public ResponseEntity<PagedModel<ClientCarModel>> getAllReservation(Pageable pageable){
        Page<ClientCarEntity> allClientCarEntity = reservationService.getAllReservation(pageable);
        PagedModel<ClientCarModel> clientCarCollectionModel = pagedResourcesAssembler.toModel(allClientCarEntity,clientCarModelAssembler);
        return new ResponseEntity<>(clientCarCollectionModel,HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<EntityModel<ClientCarOneModel>> getOneReservationById(@PathVariable("id") UUID id){
        return reservationService.getOneReservationById(id);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<EntityModel<ReservationEntity>> updateReservationById(@PathVariable("id") UUID id,@Valid @RequestBody ReservationEntity reservationToUpdate){
        return reservationService.updateReservationById(id, reservationToUpdate);
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity<ReservationEntity> updatePartialReservationById(@PathVariable("id") UUID id,@Valid @RequestBody ReservationEntity reservationToUpdate){
        return reservationService.updatePartialReservationById(id, reservationToUpdate);
    }
}

