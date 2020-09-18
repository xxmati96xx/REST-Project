package com.exemple.REST.Project.api;

import com.exemple.REST.Project.Entity.ArchCheckEntity;
import com.exemple.REST.Project.Entity.ClientCarEntity;
import com.exemple.REST.Project.assemblers.ArchCheckModelAssembler;
import com.exemple.REST.Project.model.ClientCarModel;
import com.exemple.REST.Project.model.ClientCarOneModel;
import com.exemple.REST.Project.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("api/v1/archcheck")
@RestController
public class ArchCheckController {
    private final CheckoutService checkoutService;

    @Autowired
    public ArchCheckController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }
    @Autowired
    private PagedResourcesAssembler<ArchCheckEntity> pagedResourcesAssembler;

    @Autowired
    private ArchCheckModelAssembler archCheckModelAssembler;

    @GetMapping
    public ResponseEntity<PagedModel<ClientCarModel>> getAllArchCheck(Pageable pageable){
        Page<ArchCheckEntity> allArchCheck = checkoutService.getAllArchCheck(pageable);
        PagedModel<ClientCarModel> clientCarCollectionModel = pagedResourcesAssembler.toModel(allArchCheck,archCheckModelAssembler);
        return new ResponseEntity<>(clientCarCollectionModel, HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<EntityModel<ClientCarOneModel>> getOneArchCheckById(@PathVariable("id") UUID id){
        return checkoutService.getOneArchCheckById(id);
    }
}
