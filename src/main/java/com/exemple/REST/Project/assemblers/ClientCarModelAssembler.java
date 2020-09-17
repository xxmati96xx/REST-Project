package com.exemple.REST.Project.assemblers;

import com.exemple.REST.Project.Entity.ClientCarEntity;
import com.exemple.REST.Project.api.ReservationController;
import com.exemple.REST.Project.model.CarModel;
import com.exemple.REST.Project.model.ClientCarModel;
import com.exemple.REST.Project.model.ClientModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ClientCarModelAssembler extends RepresentationModelAssemblerSupport<ClientCarEntity, ClientCarModel> {
    public ClientCarModelAssembler() {
        super(ReservationController.class, ClientCarModel.class);
    }

    @Override
    public ClientCarModel toModel(ClientCarEntity entity) {
        ClientCarModel clientCarModel = instantiateModel(entity);
        ClientModel clientModel = new ClientModel();
        CarModel carModel = new CarModel();

        clientCarModel.setId(entity.getId());

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
