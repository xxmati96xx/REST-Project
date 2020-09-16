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
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientCarModel extends RepresentationModel<ClientCarModel> {

    private UUID id;
    private ClientModel client;
    private CarModel car;
    private Date date;
    private Date dateOR;

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
}
