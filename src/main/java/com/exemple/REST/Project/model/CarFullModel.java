package com.exemple.REST.Project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarFullModel extends RepresentationModel<CarFullModel> {
    private UUID id;
    private String producer;
    private String model;
    private int hp;
    private float price;
    @Builder.Default
    private boolean rent = false;
    private int year;
    private String details;
    private Long version;


    public UUID getId() {
        return id;
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

    public int getHp() {
        return hp;
    }

    public String getDetails() {
        return details;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setModel(String model) {
        this.model = model;
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

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
