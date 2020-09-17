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
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;
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
                clientCarOneModel.setVersion(reservationEntity.getVersion());
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
        if (    !StringUtils.isEmpty(newReservation.getCar_id()) && !StringUtils.isEmpty(newReservation.getClient_id()) &&
                !StringUtils.isEmpty(newReservation.getDateOR()) && newReservation.getVersion() != null) {
            ReservationEntity reservationEntity = reservationRepository.findById(id).orElse(null);
            ClientEntity clientEntity = clientRepository.findById(newReservation.getClient_id()).orElse(null);
            CarEntity carEntity = carRepository.findById(newReservation.getCar_id()).orElse(null);
            if (clientEntity != null && carEntity != null) {
                if (reservationEntity == null) {
                    if (!carEntity.isRent() && newReservation.getDateOR().compareTo(new Date())>0) {
                        try {
                            newReservation.setId(id);
                            newReservation.setVersion(0L);
                            newReservation.setDate(new Date());
                            carEntity.setRent(true);
                            carRepository.save(carEntity);
                            EntityModel<ReservationEntity> entityEntityModel = EntityModel.of(reservationRepository.save(newReservation), linkTo(ReservationController.class).slash(newReservation.getId()).withSelfRel());
                            return new ResponseEntity<>(entityEntityModel, HttpStatus.CREATED);
                        } catch (Exception e) {

                        }
                    } else {
                        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                    }
                } else {
                    if (newReservation.getVersion() == reservationEntity.getVersion()) {
                        CarEntity car = carRepository.findById(reservationEntity.getCar_id()).orElse(null);
                        try {
                            if (newReservation.getDateOR().compareTo(newReservation.getDate()) > 0) {
                                reservationEntity.setClient_id(newReservation.getClient_id());
                                reservationEntity.setCar_id(newReservation.getCar_id());
                                reservationEntity.setDateOR(newReservation.getDateOR());
                                reservationEntity.setDate(newReservation.getDate());
                                if (car != null) {
                                    if (!car.equals(carEntity)) {
                                        car.setRent(false);
                                        carEntity.setRent(true);
                                        carRepository.save(car);
                                        carRepository.save(carEntity);
                                    }
                                } else {
                                    carEntity.setRent(true);
                                    carRepository.save(carEntity);
                                }
                                EntityModel<ReservationEntity> reservationEntityEntityModel = EntityModel.of(reservationRepository.save(reservationEntity));
                                return new ResponseEntity<>(reservationEntityEntityModel, HttpStatus.NO_CONTENT);
                            }
                        } catch(Exception e){

                        }
                    }
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ReservationEntity> updatePartialReservationById(UUID id, ReservationEntity newReservation) {
        ReservationEntity reservationEntity = reservationRepository.findById(id).orElse(null);
        if (reservationEntity != null) {
                if (!StringUtils.isEmpty(newReservation.getClient_id()) && clientRepository.findById(newReservation.getClient_id()).orElse(null) != null) {
                    reservationEntity.setClient_id(newReservation.getClient_id());
                }
            CarEntity carEntity = carRepository.findById(reservationEntity.getCar_id()).orElse(null);
            if(!StringUtils.isEmpty(newReservation.getCar_id())) {
                CarEntity carNewEntity = carRepository.findById(newReservation.getCar_id()).orElse(null);
                if (carNewEntity != null && !carEntity.isRent()) {
                    if (carEntity != null) {
                        if (!carNewEntity.equals(carEntity)) {
                            try {
                                carEntity.setRent(false);
                                carNewEntity.setRent(true);
                                carRepository.save(carEntity);
                                carRepository.save(carNewEntity);
                                reservationEntity.setCar_id(newReservation.getCar_id());
                            } catch (Exception e) {

                            }
                        }
                    } else {
                        try {
                            carNewEntity.setRent(true);
                            carRepository.save(carNewEntity);
                            reservationEntity.setCar_id(newReservation.getCar_id());
                        } catch (Exception e) {

                        }
                    }
                }
            }
            if (!StringUtils.isEmpty(newReservation.getDate())) {
                reservationEntity.setDate(newReservation.getDate());
            }
            if (!StringUtils.isEmpty(newReservation.getDateOR()) && newReservation.getDateOR().compareTo(new Date()) > 0 && newReservation.getDateOR().compareTo(reservationEntity.getDate()) > 0) {
                reservationEntity.setDateOR(newReservation.getDateOR());
            }
            return new ResponseEntity<>(reservationRepository.save(reservationEntity), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}





