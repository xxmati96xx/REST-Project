package com.exemple.REST.Project.model;

import com.exemple.REST.Project.Entity.ClientCarEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientCarModel extends RepresentationModel<ClientCarModel> {
    @org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    private Date date;
    private Date dateOR;
    private ClientModel client;
    private CarModel car;


    public void setDate(Date date) {
        this.date = date;
    }

    public void setDateOR(Date dateOR) {
        this.dateOR = dateOR;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setClient(ClientModel client) {
        this.client = client;
    }

    public void setCar(CarModel car) {
        this.car = car;
    }

    public UUID getId() {
        return id;
    }

    public ClientModel getClient() {
        return client;
    }

    public CarModel getCar() {
        return car;
    }

    public Date getDate() {
        return date;
    }

    public Date getDateOR() {
        return dateOR;
    }
}
