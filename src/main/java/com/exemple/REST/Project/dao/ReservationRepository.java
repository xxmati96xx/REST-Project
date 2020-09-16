package com.exemple.REST.Project.dao;

import com.exemple.REST.Project.Entity.CarEntity;
import com.exemple.REST.Project.Entity.ClientCarEntity;
import com.exemple.REST.Project.Entity.ReservationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ReservationRepository extends JpaRepository<ClientCarEntity, UUID>, PagingAndSortingRepository<ClientCarEntity, UUID> {
    @Query(value = "select reservation.id,client_id,car_id,fname,lname,address,producer,model,price,rent,year,date,dateOR from reservation join clients on client_id = uuid(clients.id) join cars on car_id = uuid(cars.id)",countQuery="select count(*) from reservation join clients on client_id = uuid(clients.id) join cars on car_id = uuid(cars.id)",nativeQuery = true)
    public Page<ClientCarEntity> findAllTogether(Pageable pageable);
}
