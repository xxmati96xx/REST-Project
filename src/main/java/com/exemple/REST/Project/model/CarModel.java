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
public class CarModel extends RepresentationModel<CarModel> {
    private UUID id;
    private String producer;
    private String model;
    private int hp;
    private float price;
    @Builder.Default
    private boolean rent = false;
    private int year;


    public UUID getId() {
        return id;
    }

    public int getHp() {
        return hp;
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


    public void setId(UUID id) {
        this.id = id;
    }

    public void setHp(int hp) {
        this.hp = hp;
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

}
