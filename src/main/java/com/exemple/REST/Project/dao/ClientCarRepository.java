package com.exemple.REST.Project.dao;

import com.exemple.REST.Project.Entity.ClientCarEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientCarRepository extends JpaRepository<ClientCarEntity, UUID>, PagingAndSortingRepository<ClientCarEntity, UUID> {
    @Query(value = "select checkout.id,client_id,car_id,fname,lname,address,producer,model,price,rent,year,date,dateOR,hp from checkout join clients on client_id = uuid(clients.id) join cars on car_id = uuid(cars.id)",countQuery="select count(*) from checkout join clients on client_id = uuid(clients.id) join cars on car_id = uuid(cars.id)",nativeQuery = true)
    Page<ClientCarEntity> findAllCheckout(Pageable pageable);

    @Query(value = "select archcheck.id,client_id,car_id,fname,lname,address,producer,model,price,rent,year,date,dateOR,hp from archcheck join clients on client_id = uuid(clients.id) join cars on car_id = uuid(cars.id)",countQuery="select count(*) from archcheck join clients on client_id = uuid(clients.id) join cars on car_id = uuid(cars.id)",nativeQuery = true)
    Page<ClientCarEntity> findAllArchCheck(Pageable pageable);
}


