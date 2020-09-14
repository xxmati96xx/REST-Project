package com.exemple.REST.Project.dao;

import com.exemple.REST.Project.Entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ClientRepository extends JpaRepository<ClientEntity,UUID> ,PagingAndSortingRepository<ClientEntity, UUID>{
}
