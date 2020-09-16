package com.exemple.REST.Project.api;


import com.exemple.REST.Project.Entity.CarEntity;
import com.exemple.REST.Project.Entity.ClientCarEntity;
import com.exemple.REST.Project.Entity.ClientEntity;
import com.exemple.REST.Project.Entity.ReservationEntity;
import com.exemple.REST.Project.assemblers.CarModelAssembler;
import com.exemple.REST.Project.assemblers.ClientCarModelAssembler;
import com.exemple.REST.Project.model.CarModel;
import com.exemple.REST.Project.model.ClientCarModel;
import com.exemple.REST.Project.service.CarService;
import com.exemple.REST.Project.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
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

 //   @PostMapping
 //   public ResponseEntity<ReservationEntity> addReservation(@RequestBody ReservationEntity reservationEntity)
  //  {
  //      return new ResponseEntity<>(reservationService.addReservation(reservationEntity), HttpStatus.CREATED);
   // }

    @GetMapping
    public ResponseEntity<PagedModel<ClientCarModel>> getAllTogether(Pageable pageable){
        Page<ClientCarEntity> allClientCarEntity = reservationService.getAllTogether(pageable);
        PagedModel<ClientCarModel> clientCarCollectionModel = pagedResourcesAssembler.toModel(allClientCarEntity,clientCarModelAssembler);
        return new ResponseEntity<>(clientCarCollectionModel,HttpStatus.OK);
    }
}
