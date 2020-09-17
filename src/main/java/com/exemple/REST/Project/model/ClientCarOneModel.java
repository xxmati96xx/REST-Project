package com.exemple.REST.Project.model;

import com.exemple.REST.Project.model.CarFullModel;
import com.exemple.REST.Project.model.CarModel;
import com.exemple.REST.Project.model.ClientModel;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ClientCarOneModel extends RepresentationModel<ClientCarOneModel> {
    @Id
    @Column(name = "id")
    @org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    private Date date;
    private Date dateOR;
    private ClientModel client;
    private CarFullModel car;

    public UUID getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Date getDateOR() {
        return dateOR;
    }

    public ClientModel getClient() {
        return client;
    }

    public CarFullModel getCar() {
        return car;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDateOR(Date dateOR) {
        this.dateOR = dateOR;
    }

    public void setClient(ClientModel client) {
        this.client = client;
    }

    public void setCar(CarFullModel car) {
        this.car = car;
    }
}
