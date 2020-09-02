package com.exemple.REST.Project.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Client {

    private final UUID id;
    private final String fname,lname,address;

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
