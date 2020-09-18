package com.exemple.REST.Project.api;


import com.exemple.REST.Project.Entity.*;
import com.exemple.REST.Project.model.ClientCarOneModel;
import com.exemple.REST.Project.assemblers.ClientCarModelAssembler;
import com.exemple.REST.Project.model.ClientCarModel;
import com.exemple.REST.Project.service.CheckoutService;
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

@RequestMapping("api/v1/checkouts")
@RestController
public class CheckoutController {


    private final CheckoutService checkoutService;

    @Autowired
    private PagedResourcesAssembler<ClientCarEntity> pagedResourcesAssembler;

    @Autowired
    private ClientCarModelAssembler clientCarModelAssembler;

    @Autowired
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    public ResponseEntity<Link> addCheckout()
    {
       return checkoutService.addCheckout();
    }

    @GetMapping
    public ResponseEntity<PagedModel<ClientCarModel>> getAllCheckout(Pageable pageable){
        Page<ClientCarEntity> allClientCarEntity = checkoutService.getAllCheckout(pageable);
        PagedModel<ClientCarModel> clientCarCollectionModel = pagedResourcesAssembler.toModel(allClientCarEntity,clientCarModelAssembler);
        return new ResponseEntity<>(clientCarCollectionModel,HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<EntityModel<ClientCarOneModel>> getOneCheckoutById(@PathVariable("id") UUID id){
        return checkoutService.getOneCheckoutById(id);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<EntityModel<CheckoutEntity>> updateCheckoutById(@PathVariable("id") UUID id, @Valid @RequestBody CheckoutEntity reservationToUpdate){
        return checkoutService.updateCheckoutById(id, reservationToUpdate);
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity<CheckoutEntity> updatePartialCheckoutById(@PathVariable("id") UUID id, @RequestBody CheckoutEntity reservationToUpdate){
        return checkoutService.updatePartialCheckoutById(id, reservationToUpdate);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<CheckoutEntity> deleteCheckoutById(@PathVariable("id") UUID id){
        return checkoutService.deleteCheckoutById(id);
    }
}

