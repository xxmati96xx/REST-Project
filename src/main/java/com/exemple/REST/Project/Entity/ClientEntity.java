package com.exemple.REST.Project.Entity;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class ClientEntity extends RepresentationModel<ClientEntity> {
    @Id
    @Column(name = "id")
    @GeneratedValue
    @org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;

    @NotBlank
    private  String fname;

    @NotBlank
    private String lname;

    @NotBlank
    private String address;
    @Version
    private Long version;

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
