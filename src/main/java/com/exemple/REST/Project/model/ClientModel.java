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
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientModel extends RepresentationModel<ClientModel> {

    private UUID id;

    private  String fname;

    private String lname;

    private String address;

    private Long version;

    public void setId(UUID id) {
        this.id = id;
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
