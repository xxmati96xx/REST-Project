package com.exemple.REST.Project.assemblers;

import com.exemple.REST.Project.Entity.ArchCheckEntity;
import com.exemple.REST.Project.Entity.ClientCarEntity;
import com.exemple.REST.Project.api.ArchCheckController;
import com.exemple.REST.Project.api.CheckoutController;
import com.exemple.REST.Project.model.CarModel;
import com.exemple.REST.Project.model.ClientCarModel;
import com.exemple.REST.Project.model.ClientModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class ArchCheckModelAssembler extends RepresentationModelAssemblerSupport<ArchCheckEntity, ClientCarModel> {
    public ArchCheckModelAssembler() {
        super(ArchCheckController.class, ClientCarModel.class);
    }

    @Override
    public ClientCarModel toModel(ArchCheckEntity entity) {
        ClientCarModel clientCarModel = instantiateModel(entity);
        ClientModel clientModel = new ClientModel();
        CarModel carModel = new CarModel();

        clientCarModel.setId(entity.getId());
        clientCarModel.add(linkTo(ArchCheckController.class).slash(clientCarModel.getId()).withSelfRel());
        clientModel.setId(entity.getClient_id());
        clientModel.setFname(entity.getFname());
        clientModel.setLname(entity.getLname());
        clientModel.setAddress(entity.getAddress());

        carModel.setId(entity.getCar_id());
        carModel.setProducer(entity.getProducer());
        carModel.setModel(entity.getModel());
        carModel.setPrice(entity.getPrice());
        carModel.setRent(entity.isRent());
        carModel.setYear(entity.getYear());
        carModel.setHp(entity.getHp());

        clientCarModel.setDate(entity.getDate());
        clientCarModel.setDateOR(entity.getDateOR());

        clientCarModel.setClient(clientModel);
        clientCarModel.setCar(carModel);
        return clientCarModel;
    }

}

