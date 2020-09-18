package com.exemple.REST.Project.Entity;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "archcheck")
public class ArchCheckEntity extends RepresentationModel<ArchCheckEntity> {
    @Id
    @Column(name = "id")
    @org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    //@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    private UUID client_id;
    //@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    private UUID car_id;
    private Date date;
    private Date dateOR;
    private String fname;
    private String lname;
    private String address;
    private String vin;
    private String producer;
    private String model;
    private int hp;
    private float price;
    private boolean rent = false;
    private int year;
    private String details;

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

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setRent(boolean rent) {
        this.rent = rent;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDetails(String details) {
        this.details = details;
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

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getAddress() {
        return address;
    }

    public String getVin() {
        return vin;
    }

    public String getProducer() {
        return producer;
    }

    public String getModel() {
        return model;
    }

    public int getHp() {
        return hp;
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

    public String getDetails() {
        return details;
    }
}
