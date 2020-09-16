package com.exemple.REST.Project.service;


import com.exemple.REST.Project.Entity.CarEntity;
import com.exemple.REST.Project.Entity.ClientCarEntity;
import com.exemple.REST.Project.Entity.ReservationEntity;
import com.exemple.REST.Project.dao.CarRepository;
import com.exemple.REST.Project.dao.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

 //   public ReservationEntity addReservation(ReservationEntity reservation){
  //      reservation.setId(getUUID());
 //       return reservationRepository.save(reservation);
   // }

    public Page<ClientCarEntity> getAllTogether(Pageable pageable){
        Page<ClientCarEntity> page = reservationRepository.findAllTogether(pageable);
        return page;
    }

    public UUID getUUID(){
        UUID tmpId = UUID.randomUUID();
        //uuidList.add(tmpId);
        return tmpId;
    }
}
