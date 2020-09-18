package com.exemple.REST.Project.dao;

import com.exemple.REST.Project.Entity.CheckoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CheckoutRepository extends JpaRepository<CheckoutEntity, UUID>, PagingAndSortingRepository<CheckoutEntity, UUID> {
    //@Query(value = "select reservation.id,client_id,car_id,fname,lname,address,producer,model,price,rent,year,date,dateOR from reservation join clients on client_id = uuid(clients.id) join cars on car_id = uuid(cars.id)",countQuery="select count(*) from reservation join clients on client_id = uuid(clients.id) join cars on car_id = uuid(cars.id)",nativeQuery = true)
    //public Page<ClientCarModelQuery> findAllTogether(Pageable pageable);
    @Query(value = "select * from checkout where checkout.client_id = ?",nativeQuery = true)
    List<CheckoutEntity> findAllByClient_Id (UUID client_id);
}
