package com.exemple.REST.Project.Entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ClientCarEntity {
    @Id
    @Column(name = "id")
    @org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    @org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    private UUID client_id;
    @org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    private UUID car_id;
    private Date date;
    private Date dateOR;
    private  String fname;
    private String lname;
    private String address;
    private String producer;
    private String model;
    private float price;
    private boolean rent;
    private int year;


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

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getAddress() {
        return address;
    }

    public String getProducer() {
        return producer;
    }

    public String getModel() {
        return model;
    }

    public float getPrice() {
        return price;
    }

    public boolean isRent() {
        return rent;
    }

    public int getYear() {
        return year;
    }
}
