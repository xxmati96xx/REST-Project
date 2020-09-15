package com.exemple.REST.Project.dao;


import com.exemple.REST.Project.Entity.CarEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CarRepository extends JpaRepository<CarEntity,UUID> ,PagingAndSortingRepository<CarEntity, UUID>{
}

