package com.exemple.REST.Project.Entity;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
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
@Table(name = "checkout")
public class CheckoutEntity extends RepresentationModel<CheckoutEntity> {

    @Id
    @Column(name = "id")
    @org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    //@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    private UUID client_id;
    //@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    private UUID car_id;
    @NotNull
    private Date date;
    @NotNull
    private Date dateOR;
    @NotNull
    @Version
    private Long version;

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

    public void setVersion(Long version) {
        this.version = version;
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

    public Long getVersion() {
        return version;
    }
}
