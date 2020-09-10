package com.exemple.REST.Project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;


import javax.persistence.Entity;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Client extends RepresentationModel<Client> {
    private final UUID id;

    @NotBlank
    private final String fname;

    @NotBlank
    private final String lname;

    @NotBlank
    private final String address;

    public Client(@JsonProperty("id") UUID id,
                  @JsonProperty("fname") String fname,
                  @JsonProperty("lname") String lname,
                  @JsonProperty("address") String address) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.address = address;
    }

    public UUID getId() {
        return id;
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
}
