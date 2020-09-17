package com.exemple.REST.Project.service;


import com.exemple.REST.Project.Entity.*;
import com.exemple.REST.Project.model.ClientCarOneModel;
import com.exemple.REST.Project.api.CarController;
import com.exemple.REST.Project.api.ReservationController;
import com.exemple.REST.Project.dao.CarRepository;

import com.exemple.REST.Project.dao.ClientCarRepository;
import com.exemple.REST.Project.dao.ClientRepository;
import com.exemple.REST.Project.dao.ReservationRepository;
import com.exemple.REST.Project.model.CarFullModel;
import com.exemple.REST.Project.model.ClientModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class ReservationService {

    @Autowired
    private ClientCarRepository clientCarRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private RepresentationModelAssembler<ClientEntity,ClientModel> clientRepresentationModelAssembler;
    @Autowired
    private RepresentationModelAssembler<CarEntity,CarFullModel> carRepresentationModelAssembler;

    private LinkedList<UUID> uuidList = new LinkedList<>();

    public ResponseEntity<Link> addReservation(){
        UUID tmpId = getUUID();
        uuidList.add(tmpId);
        Link link = linkTo(ReservationController.class).slash(tmpId).withSelfRel();
        return new ResponseEntity<>(link,HttpStatus.CREATED);
    }

    public Page<ClientCarEntity> getAllReservation(Pageable pageable){
        return clientCarRepository.findAllTogether(pageable);
    }

    public UUID getUUID(){
        UUID tmpId = UUID.randomUUID();
        //uuidList.add(tmpId);
        return tmpId;
    }

    public ResponseEntity<EntityModel<ClientCarOneModel>> getOneReservationById(UUID id){
        ReservationEntity reservationEntity = reservationRepository.findById(id).orElse(null);
        if (reservationEntity != null){
            ClientCarOneModel clientCarOneModel= new ClientCarOneModel();
            clientCarOneModel.setId(reservationEntity.getId());
            ClientEntity clientEntity = clientRepository.findById(reservationEntity.getClient_id()).orElse(null);
            CarEntity carEntity = carRepository.findById(reservationEntity.getCar_id()).orElse(null);
            if (clientEntity != null && carEntity != null) {
                ClientModel clientModel = clientRepresentationModelAssembler.toModel(clientEntity);
                CarFullModel carFullModel = carRepresentationModelAssembler.toModel(carEntity);
                //clientModel.add(linkTo(ClientController.class).slash(clientEntity.getId()).withSelfRel());
                //carFullModel.add(linkTo(CarController.class).slash(carEntity.getId()).withSelfRel());
                clientCarOneModel.setClient(clientModel);
                clientCarOneModel.setCar(carFullModel);
                clientCarOneModel.setDate(reservationEntity.getDate());
                clientCarOneModel.setDateOR(reservationEntity.getDateOR());
                Link link = linkTo(ReservationController.class).slash(id).withSelfRel();
                Link linkAll = linkTo(ReservationController.class).withRel("All reservations");
                EntityModel<ClientCarOneModel> entityEntityModel = EntityModel.of(clientCarOneModel,link,linkAll);
                return new ResponseEntity<>(entityEntityModel, HttpStatus.OK);
            }
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<EntityModel<ReservationEntity>> updateReservationById(UUID id, ReservationEntity newReservation){
        ReservationEntity reservationEntity = reservationRepository.findById(id).orElse(null);
        ClientEntity clientEntity = clientRepository.findById(newReservation.getClient_id()).orElse(null);
        CarEntity carEntity = carRepository.findById(newReservation.getCar_id()).orElse(null);
        if (clientEntity != null && carEntity != null && !carEntity.isRent() ){
            if (reservationEntity == null){
                try {
                    newReservation.setId(id);
                    carEntity.setRent(true);
                    reservationRepository.save(newReservation);
                    carRepository.save(carEntity);
                    EntityModel<ReservationEntity> entityEntityModel = EntityModel.of(newReservation,linkTo(ReservationController.class).slash(newReservation.getId()).withSelfRel());
                    return new ResponseEntity<>(entityEntityModel,HttpStatus.CREATED);
                }catch (Exception e) {

                }

            }else
            {
                reservationEntity.setClient_id(newReservation.getClient_id());
                reservationEntity.//dokończ
            }


        }

        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }


