package com.exemple.REST.Project.service;


import com.exemple.REST.Project.Entity.*;
import com.exemple.REST.Project.dao.*;
import com.exemple.REST.Project.model.CarModel;
import com.exemple.REST.Project.model.ClientCarOneModel;
import com.exemple.REST.Project.api.CheckoutController;

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
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class CheckoutService {

    @Autowired
    private ClientCarRepository clientCarRepository;
    @Autowired
    private CheckoutRepository checkoutRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ArchCheckRepository archCheckRepository;
    @Autowired
    private RepresentationModelAssembler<ClientEntity,ClientModel> clientRepresentationModelAssembler;
    @Autowired
    private RepresentationModelAssembler<CarEntity,CarFullModel> carRepresentationModelAssembler;
    @Autowired
    private RepresentationModelAssembler<ArchCheckEntity,ClientCarOneModel> clientCarOneRepresentationModelAssembler;

    private LinkedList<UUID> uuidList = new LinkedList<>();

    public ResponseEntity<Link> addCheckout(){
        UUID tmpId = getUUID();
        uuidList.add(tmpId);
        Link link = linkTo(CheckoutController.class).slash(tmpId).withSelfRel();
        return new ResponseEntity<>(link,HttpStatus.CREATED);
    }

    public Page<ClientCarEntity> getAllCheckout(Pageable pageable){
        return clientCarRepository.findAllCheckout(pageable);
    }

    public Page<ArchCheckEntity> getAllArchCheck(Pageable pageable){
        return archCheckRepository.findAll(pageable);
    }

    public UUID getUUID(){
        return UUID.randomUUID();
    }

    public ResponseEntity<EntityModel<ClientCarOneModel>> getOneCheckoutById(UUID id){
        CheckoutEntity checkoutEntity = checkoutRepository.findById(id).orElse(null);
        if (checkoutEntity != null){
            ClientCarOneModel clientCarOneModel= new ClientCarOneModel();
            clientCarOneModel.setId(checkoutEntity.getId());
            ClientEntity clientEntity = clientRepository.findById(checkoutEntity.getClient_id()).orElse(null);
            CarEntity carEntity = carRepository.findById(checkoutEntity.getCar_id()).orElse(null);
            if (clientEntity != null && carEntity != null) {
                ClientModel clientModel = clientRepresentationModelAssembler.toModel(clientEntity);
                CarFullModel carFullModel = carRepresentationModelAssembler.toModel(carEntity);
                //clientModel.add(linkTo(ClientController.class).slash(clientEntity.getId()).withSelfRel());
                //carFullModel.add(linkTo(CarController.class).slash(carEntity.getId()).withSelfRel());
                clientCarOneModel.setClient(clientModel);
                clientCarOneModel.setCar(carFullModel);
                clientCarOneModel.setDate(checkoutEntity.getDate());
                clientCarOneModel.setDateOR(checkoutEntity.getDateOR());
                clientCarOneModel.setVersion(checkoutEntity.getVersion());
                Link link = linkTo(CheckoutController.class).slash(id).withSelfRel();
                Link linkAll = linkTo(CheckoutController.class).withRel("All checkouts");
                EntityModel<ClientCarOneModel> entityEntityModel = EntityModel.of(clientCarOneModel,link,linkAll);
                return new ResponseEntity<>(entityEntityModel, HttpStatus.OK);
            }
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<EntityModel<ClientCarOneModel>> getOneArchCheckById(UUID id) {
        ArchCheckEntity archCheckEntity = archCheckRepository.findById(id).orElse(null);
        if (archCheckEntity != null) {
            ClientCarOneModel clientCarOneModel = clientCarOneRepresentationModelAssembler.toModel(archCheckEntity);
            Link link = linkTo(CheckoutController.class).slash(id).withSelfRel();
            Link linkAll = linkTo(CheckoutController.class).withRel("All archcheck");
            EntityModel<ClientCarOneModel> entityModel = EntityModel.of(clientCarOneModel,link,linkAll);
            return new ResponseEntity<>(entityModel,HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);    }

    public ResponseEntity<EntityModel<CheckoutEntity>> updateCheckoutById(UUID id, CheckoutEntity newCheckout){
        if (    !StringUtils.isEmpty(newCheckout.getCar_id()) && !StringUtils.isEmpty(newCheckout.getClient_id()) &&
                !StringUtils.isEmpty(newCheckout.getDateOR()) && newCheckout.getVersion() != null) {
            CheckoutEntity checkoutEntity = checkoutRepository.findById(id).orElse(null);
            ClientEntity clientEntity = clientRepository.findById(newCheckout.getClient_id()).orElse(null);
            CarEntity carEntity = carRepository.findById(newCheckout.getCar_id()).orElse(null);
            if (clientEntity != null && carEntity != null) {
                if (checkoutEntity == null) {
                    if (!carEntity.isRent() && newCheckout.getDateOR().compareTo(new Date())>0) {
                        try {
                            newCheckout.setId(id);
                            newCheckout.setVersion(0L);
                            newCheckout.setDate(new Date());
                            carEntity.setRent(true);
                            carRepository.save(carEntity);
                            EntityModel<CheckoutEntity> entityEntityModel = EntityModel.of(checkoutRepository.save(newCheckout), linkTo(CheckoutController.class).slash(newCheckout.getId()).withSelfRel());
                            return new ResponseEntity<>(entityEntityModel, HttpStatus.CREATED);
                        } catch (Exception e) {

                        }
                    } else {
                        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                    }
                } else {
                    if (newCheckout.getVersion().equals(checkoutEntity.getVersion())) {
                        CarEntity car = carRepository.findById(checkoutEntity.getCar_id()).orElse(null);
                        try {
                            if (newCheckout.getDateOR().compareTo(newCheckout.getDate()) > 0) {
                                checkoutEntity.setClient_id(newCheckout.getClient_id());
                                checkoutEntity.setCar_id(newCheckout.getCar_id());
                                checkoutEntity.setDateOR(newCheckout.getDateOR());
                                checkoutEntity.setDate(newCheckout.getDate());
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
                                EntityModel<CheckoutEntity> reservationEntityEntityModel = EntityModel.of(checkoutRepository.save(checkoutEntity));
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

    public ResponseEntity<CheckoutEntity> updatePartialCheckoutById(UUID id, CheckoutEntity newCheckout) {
        CheckoutEntity checkoutEntity = checkoutRepository.findById(id).orElse(null);
        if (checkoutEntity != null) {
                if (!StringUtils.isEmpty(newCheckout.getClient_id()) && clientRepository.findById(newCheckout.getClient_id()).orElse(null) != null) {
                    checkoutEntity.setClient_id(newCheckout.getClient_id());
                }
            CarEntity carEntity = carRepository.findById(checkoutEntity.getCar_id()).orElse(null);
            if(!StringUtils.isEmpty(newCheckout.getCar_id())) {
                CarEntity carNewEntity = carRepository.findById(newCheckout.getCar_id()).orElse(null);
                if (carNewEntity != null && !carEntity.isRent()) {
                    if (carEntity != null) {
                        if (!carNewEntity.equals(carEntity)) {
                            try {
                                carEntity.setRent(false);
                                carNewEntity.setRent(true);
                                carRepository.save(carEntity);
                                carRepository.save(carNewEntity);
                                checkoutEntity.setCar_id(newCheckout.getCar_id());
                            } catch (Exception e) {

                            }
                        }
                    } else {
                        try {
                            carNewEntity.setRent(true);
                            carRepository.save(carNewEntity);
                            checkoutEntity.setCar_id(newCheckout.getCar_id());
                        } catch (Exception e) {

                        }
                    }
                }
            }
            if (!StringUtils.isEmpty(newCheckout.getDate())) {
                checkoutEntity.setDate(newCheckout.getDate());
            }
            if (!StringUtils.isEmpty(newCheckout.getDateOR()) && newCheckout.getDateOR().compareTo(new Date()) > 0 && newCheckout.getDateOR().compareTo(checkoutEntity.getDate()) > 0) {
                checkoutEntity.setDateOR(newCheckout.getDateOR());
            }
            return new ResponseEntity<>(checkoutRepository.save(checkoutEntity), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<CheckoutEntity> deleteCheckoutById(UUID id){
        CheckoutEntity checkoutEntity = checkoutRepository.findById(id).orElse(null);
        if(checkoutEntity != null) {
            try{
                CarEntity carEntity = carRepository.findById(checkoutEntity.getCar_id()).orElse(null);
                if (carEntity != null){
                    carEntity.setRent(false);
                    carRepository.save(carEntity);
                    ClientEntity clientEntity = clientRepository.findById(checkoutEntity.getClient_id()).orElse(null);
                    if (clientEntity != null) {
                        ArchCheckEntity archCheckEntity = new ArchCheckEntity();
                        archCheckEntity.setId(checkoutEntity.getId());archCheckEntity.setClient_id(checkoutEntity.getClient_id());archCheckEntity.setCar_id(checkoutEntity.getCar_id());
                        archCheckEntity.setDate(checkoutEntity.getDate());archCheckEntity.setDateOR(checkoutEntity.getDateOR());archCheckEntity.setFname(clientEntity.getFname());
                        archCheckEntity.setLname(clientEntity.getLname());archCheckEntity.setAddress(clientEntity.getAddress());archCheckEntity.setProducer(carEntity.getProducer());
                        archCheckEntity.setModel(carEntity.getModel());archCheckEntity.setHp(carEntity.getHp());archCheckEntity.setPrice(carEntity.getPrice());
                        archCheckEntity.setYear(carEntity.getYear());archCheckEntity.setDetails(carEntity.getDetails());archCheckEntity.setVin(carEntity.getVin());
                        archCheckRepository.save(archCheckEntity);
                        checkoutRepository.delete(checkoutEntity);
                        return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
                    }
                }

            }
            catch (Exception e){

            }
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

}





