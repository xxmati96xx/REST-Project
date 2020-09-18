package com.exemple.REST.Project.assemblers;

import com.exemple.REST.Project.Entity.CarEntity;
import com.exemple.REST.Project.Entity.ClientEntity;
import com.exemple.REST.Project.api.CarController;
import com.exemple.REST.Project.api.ClientController;
import com.exemple.REST.Project.model.CarFullModel;
import com.exemple.REST.Project.model.CarModel;
import com.exemple.REST.Project.model.ClientModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CarFullModelAssembler extends RepresentationModelAssemblerSupport<CarEntity, CarFullModel> {
    public CarFullModelAssembler() {
        super(CarController.class, CarFullModel.class);
    }

    @Override
    public CarFullModel toModel(CarEntity entity) {
        CarFullModel carFullModel = instantiateModel(entity);
        carFullModel.add(linkTo(CarController.class).slash((entity.getId()))
                .withSelfRel());
        carFullModel.setId(entity.getId());
        carFullModel.setProducer(entity.getProducer());
        carFullModel.setModel(entity.getModel());
        carFullModel.setPrice(entity.getPrice());
        carFullModel.setYear(entity.getYear());
        carFullModel.setRent(entity.isRent());
        carFullModel.setHp(entity.getHp());
        carFullModel.setVin(entity.getVin());
        carFullModel.setDetails(entity.getDetails());

        return carFullModel;
    }
}
