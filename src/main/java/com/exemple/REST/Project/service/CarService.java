package com.exemple.REST.Project.service;

import com.exemple.REST.Project.Entity.CarEntity;
import com.exemple.REST.Project.Entity.ClientEntity;
import com.exemple.REST.Project.dao.CarRepository;

import com.exemple.REST.Project.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

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

    public String deleteCarById(UUID id){
        carRepository.deleteById(id);
        return "Remove client: "+ id;
    }
    public CarEntity updateCarById(UUID id,CarEntity newCar) {
        CarEntity car = carRepository.findById(id).orElse(newCar);
        if (car.getId() == null) {
            for (UUID uuid:uuidList) {
                if (id.equals(uuid)) {
                    car.setId(id);
                    uuidList.remove(uuid);
                    return carRepository.save(car);
                }
            }
        }
        else
        if (    !StringUtils.isEmpty(newCar.getVin()) &&
                !StringUtils.isEmpty(newCar.getProducer()) &&
                !StringUtils.isEmpty(newCar.getModel()) &&
                newCar.getHp() > 0 &&
                newCar.getPrice() > 0 &&
                (newCar.getRent() == true || newCar.getRent() == false) &&
                newCar.getYear() <= Calendar.getInstance().get(Calendar.YEAR) &&
                !StringUtils.isEmpty(newCar.getDetails())) {
            car.setVin(newCar.getVin());
            car.setProducer(newCar.getProducer());
            car.setModel(newCar.getModel());
            car.setHp(newCar.getHp());
            car.setPrice(newCar.getPrice());
            car.setRent(newCar.getRent());
            car.setYear(newCar.getYear());
            car.setDetails(newCar.getDetails());
            carRepository.save(car);
            return car;
        }
        return null;

    }
    public UUID getUUID(){
        UUID tmpId = UUID.randomUUID();
        uuidList.add(tmpId);
        return tmpId;
    }
        /*


    public int updatePartialClientById(UUID id,Client newClient){
        ClientEntity client = clientRepository.findById(id).orElse(null);
        if(client.getId() != null) {
            /*
            if(newClient.getFname()!= null) {
                client.setFname(newClient.getFname());
            }
            if(newClient.getLname() != null) {
                client.setLname(newClient.getLname());
            }
            if(newClient.getAddress()!=null) {
                client.setAddress(newClient.getAddress());
            }
            */
    /*
            Optional.ofNullable(newClient.getFname())
                    .filter(fname -> !StringUtils.isEmpty(fname))
                    .map(StringUtils::capitalize)
                    .ifPresent(fname -> client.setFname(newClient.getFname()));
            Optional.ofNullable(newClient.getLname())
                    .filter(lname -> !StringUtils.isEmpty(lname))
                    .map(StringUtils::capitalize)
                    .ifPresent(lirstName -> client.setLname(newClient.getLname()));
            Optional.ofNullable(newClient.getAddress())
                    .filter(address -> !StringUtils.isEmpty(address))
                    .ifPresent(address -> client.setAddress(newClient.getAddress()));
            clientRepository.save(client);
            return 1;
        }
        return 0;
    }

     */
}

