package com.exemple.REST.Project.assemblers;

import com.exemple.REST.Project.Entity.ClientEntity;
import com.exemple.REST.Project.api.ClientController;
import com.exemple.REST.Project.model.ClientModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class ClientModelAssembler extends RepresentationModelAssemblerSupport<ClientEntity, ClientModel> {

    public ClientModelAssembler() {
        super(ClientController.class, ClientModel.class);
    }

    @Override
    public ClientModel toModel(ClientEntity entity) {
        ClientModel clientModel = instantiateModel(entity);
        clientModel.add(linkTo(ClientController.class).slash((entity.getId()))
                .withSelfRel());


        clientModel.setId(entity.getId());
        clientModel.setFname(entity.getFname());
        clientModel.setLname(entity.getLname());
        clientModel.setAddress(entity.getAddress());


        return clientModel;
    }
}

