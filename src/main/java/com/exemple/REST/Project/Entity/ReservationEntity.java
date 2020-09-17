package com.exemple.REST.Project.Entity;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservation")
public class ReservationEntity extends RepresentationModel<ReservationEntity> {

    @Id
    @Column(name = "id")
    @org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    //@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    private UUID client_id;
    //@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    private UUID car_id;

    @javax.persistence.Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();

    private Date dateOR;

    public void setId(UUID id) {
        this.id = id;
    }

    public void setClient_id(UUID client_id) {
        this.client_id = client_id;
    }

    public void setCar_id(UUID car_id) {
        this.car_id = car_id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDateOR(Date dateOR) {
        this.dateOR = dateOR;
    }

    public UUID getId() {
        return id;
    }

    public UUID getClient_id() {
        return client_id;
    }

    public UUID getCar_id() {
        return car_id;
    }

    public Date getDate() {
        return date;
    }

    public Date getDateOR() {
        return dateOR;
    }
}
