package com.exemple.REST.Project.dao;


import com.exemple.REST.Project.Entity.CarEntity;

import com.exemple.REST.Project.model.CarModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.UUID;
@Repository
public interface CarRepository extends JpaRepository<CarEntity,UUID> ,PagingAndSortingRepository<CarEntity, UUID>{
    //@Query(value = "SELECT id, producer,model,price,year,rent,details,hp,price,version,vin FROM cars",nativeQuery = true)
    //public Page<CarEntity> findAllCarLite(Pageable pageable);
}

