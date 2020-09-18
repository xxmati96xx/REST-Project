package com.exemple.REST.Project.dao;

import com.exemple.REST.Project.Entity.ArchCheckEntity;
import com.exemple.REST.Project.Entity.CheckoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArchCheckRepository extends JpaRepository<ArchCheckEntity, UUID>, PagingAndSortingRepository<ArchCheckEntity, UUID> {

}
