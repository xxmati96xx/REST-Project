package com.exemple.REST.Project.service;

import com.exemple.REST.Project.Entity.CarEntity;
import com.exemple.REST.Project.Entity.ClientEntity;
import com.exemple.REST.Project.api.CarController;
import com.exemple.REST.Project.api.ClientController;
import com.exemple.REST.Project.dao.CarRepository;

import com.exemple.REST.Project.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class CarService {

    private LinkedList<UUID> uuidList = new LinkedList<UUID>();

    @Autowired
    private CarRepository carRepository;

    public CarEntity addCar(CarEntity car){
        return carRepository.save(car);
    }

    public Page<CarEntity> getAllCar(Pageable pageable){
        return carRepository.findAll(pageable);
    }

    public Optional<CarEntity> getCarById(UUID id){
        return carRepository.findById(id);
    }

    public ResponseEntity<CarEntity> deleteCarById(UUID id){
        CarEntity car = carRepository.findById(id).orElse(null);
        if(car != null) {
            carRepository.delete(car);
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
    public ResponseEntity<EntityModel<CarEntity>> updateCarById(UUID id, CarEntity newCar) {
        CarEntity car = carRepository.findById(id).orElse(newCar);
        if (car.getId() == null &&
                !StringUtils.isEmpty(newCar.getVin()) &&
                !StringUtils.isEmpty(newCar.getProducer()) &&
                !StringUtils.isEmpty(newCar.getModel()) &&
                newCar.getHp() > 0 &&
                newCar.getPrice() > 0 &&
                (newCar.getRent() == true || newCar.getRent() == false) &&
                newCar.getYear() <= Calendar.getInstance().get(Calendar.YEAR) &&
                !StringUtils.isEmpty(newCar.getDetails())) {
                    for (UUID uuid:uuidList) {
                        if (id.equals(uuid)) {
                            car.setId(id);
                            Link link = linkTo(CarController.class).slash(car.getId()).withSelfRel();
                            Link linkAll = linkTo(CarController.class).withRel("All car");
                            uuidList.remove(uuid);
                            EntityModel<CarEntity> carEntityEntityModel = EntityModel.of(carRepository.save(car),link,linkAll);
                            return new ResponseEntity<>(carEntityEntityModel, HttpStatus.CREATED);
                        }
                    }
        }
        else if(!StringUtils.isEmpty(newCar.getVin()) &&
                !StringUtils.isEmpty(newCar.getProducer()) &&
                !StringUtils.isEmpty(newCar.getModel()) &&
                newCar.getHp() > 0 &&
                newCar.getPrice() > 0 &&
                (newCar.getRent() == true || newCar.getRent() == false) &&
                newCar.getYear() <= Calendar.getInstance().get(Calendar.YEAR) &&
                !StringUtils.isEmpty(newCar.getDetails()))
        {
            car.setVin(newCar.getVin());
            car.setProducer(newCar.getProducer());
            car.setModel(newCar.getModel());
            car.setHp(newCar.getHp());
            car.setPrice(newCar.getPrice());
            car.setRent(newCar.getRent());
            car.setYear(newCar.getYear());
            car.setDetails(newCar.getDetails());
            Link link = linkTo(ClientController.class).slash(car.getId()).withSelfRel();
            EntityModel<CarEntity> carEntityEntityModel = EntityModel.of(carRepository.save(car),link);
            return new ResponseEntity<>(carEntityEntityModel,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);

    }
    public UUID getUUID(){
        UUID tmpId = UUID.randomUUID();
        uuidList.add(tmpId);
        return tmpId;
    }



    public ResponseEntity<CarEntity> updatePartialCarById(UUID id, CarEntity newCar){
        CarEntity car = carRepository.findById(id).orElse(null);
        if(car != null) {
            Optional.ofNullable(newCar.getVin())
                    .filter(vin -> !StringUtils.isEmpty(vin))
                    .ifPresent(vin -> car.setVin(newCar.getVin()));
            Optional.ofNullable(newCar.getProducer())
                    .filter(producer -> !StringUtils.isEmpty(producer))
                    .map(StringUtils::capitalize)
                    .ifPresent(producer -> car.setProducer(newCar.getProducer()));
            Optional.ofNullable(newCar.getModel())
                    .filter(model -> !StringUtils.isEmpty(model))
                    .map(StringUtils::capitalize)
                    .ifPresent(model -> car.setModel(newCar.getModel()));
            Optional.ofNullable(newCar.getHp())
                    .filter(hp -> hp > 0)
                    .ifPresent(hp -> car.setHp(newCar.getHp()));
            Optional.ofNullable(newCar.getPrice())
                    .filter(price -> price > 0)
                    .ifPresent(price -> car.setPrice(newCar.getPrice()));
            Optional.ofNullable(newCar.getYear())
                    .filter(year -> year <= Calendar.getInstance().get(Calendar.YEAR))
                    .ifPresent(year -> car.setYear(newCar.getYear()));
            Optional.ofNullable(newCar.getRent())
                    .filter(rent -> (rent == false || rent == true))
                    .ifPresent(rent -> car.setRent(newCar.getRent()));
            Optional.ofNullable(newCar.getDetails())
                    .filter(details -> !StringUtils.isEmpty(details))
                    .ifPresent(details -> car.setDetails(newCar.getDetails()));

            return new ResponseEntity<>(carRepository.save(car), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }


}

