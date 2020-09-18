package com.exemple.REST.Project.assemblers;

import com.exemple.REST.Project.Entity.ArchCheckEntity;
import com.exemple.REST.Project.api.ArchCheckController;
import com.exemple.REST.Project.model.*;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class ArchCheckOneModelAssembler extends RepresentationModelAssemblerSupport<ArchCheckEntity, ClientCarOneModel> {
    public ArchCheckOneModelAssembler() {
        super(ArchCheckController.class, ClientCarOneModel.class);
    }


    @Override
    public ClientCarOneModel toModel(ArchCheckEntity entity) {
        ClientCarOneModel clientCarOneModel = instantiateModel(entity);
        ClientModel clientModel = new ClientModel();
        CarFullModel carFullModel = new CarFullModel();

        clientCarOneModel.setId(entity.getId());
        clientCarOneModel.add(linkTo(ArchCheckController.class).slash(clientCarOneModel.getId()).withSelfRel());
        clientModel.setId(entity.getClient_id());
        clientModel.setFname(entity.getFname());
        clientModel.setLname(entity.getLname());
        clientModel.setAddress(entity.getAddress());
        clientModel.setVersion(0L);

        carFullModel.setId(entity.getCar_id());
        carFullModel.setProducer(entity.getProducer());
        carFullModel.setModel(entity.getModel());
        carFullModel.setPrice(entity.getPrice());
        carFullModel.setRent(entity.isRent());
        carFullModel.setYear(entity.getYear());
        carFullModel.setHp(entity.getHp());
        carFullModel.setVin(entity.getVin());
        carFullModel.setDetails(entity.getDetails());
        carFullModel.setVersion(0L);


        clientCarOneModel.setDate(entity.getDate());
        clientCarOneModel.setDateOR(entity.getDateOR());
        clientCarOneModel.setVersion(0L);
        clientCarOneModel.setClient(clientModel);
        clientCarOneModel.setCar(carFullModel);
        return clientCarOneModel;
    }
}
