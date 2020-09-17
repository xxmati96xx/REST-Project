package com.exemple.REST.Project.assemblers;

import com.exemple.REST.Project.Entity.CarEntity;
import com.exemple.REST.Project.Entity.ClientEntity;
import com.exemple.REST.Project.api.CarController;
import com.exemple.REST.Project.api.ClientController;
import com.exemple.REST.Project.model.CarModel;
import com.exemple.REST.Project.model.ClientModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CarModelAssembler extends RepresentationModelAssemblerSupport<CarEntity, CarModel> {
    public CarModelAssembler() {
        super(CarController.class, CarModel.class);
    }

    @Override
    public CarModel toModel(CarEntity entity) {
        CarModel carModel = instantiateModel(entity);
        carModel.add(linkTo(CarController.class).slash((entity.getId()))
                .withSelfRel());
        carModel.setId(entity.getId());
        carModel.setProducer(entity.getProducer());
        carModel.setModel(entity.getModel());
        carModel.setPrice(entity.getPrice());
        carModel.setYear(entity.getYear());
        carModel.setRent(entity.isRent());
        carModel.setHp(entity.getHp());
        return carModel;
    }
}
