package com.exemple.REST.Project.dao;

import com.exemple.REST.Project.Entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, UUID>, PagingAndSortingRepository<ReservationEntity, UUID> {
    //@Query(value = "select reservation.id,client_id,car_id,fname,lname,address,producer,model,price,rent,year,date,dateOR from reservation join clients on client_id = uuid(clients.id) join cars on car_id = uuid(cars.id)",countQuery="select count(*) from reservation join clients on client_id = uuid(clients.id) join cars on car_id = uuid(cars.id)",nativeQuery = true)
    //public Page<ClientCarModelQuery> findAllTogether(Pageable pageable);
}
