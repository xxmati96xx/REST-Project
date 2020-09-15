package com.exemple.REST.Project.Entity;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class CarEntity extends RepresentationModel<CarEntity> {
    @Id
    @Column(name = "id")
    @GeneratedValue
    @org.hibernate.annotations.Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    @NotBlank
    private String vin;
    @NotBlank
    private String producer;
    @NotBlank
    private String model;
    private int hp;
    private float price;
    private boolean rent = false;
    private int year;
    private String details;
    @Version
    private Long version;

    public UUID getId() {
        return id;
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

    public Float getPrice() {
        return price;
    }

    public Boolean getRent() {
        return rent;
    }

    public int getYear() {
        return year;
    }

    public String getDetails() {
        return details;
    }

    public Long getVersion() {
        return version;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setRent(Boolean rent) {
        this.rent = rent;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}