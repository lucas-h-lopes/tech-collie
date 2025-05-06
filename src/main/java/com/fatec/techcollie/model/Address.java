package com.fatec.techcollie.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "street", length = 200)
    private String street;
    @Column(name = "district", length = 200)
    private String district;
    @Column(name = "number", length = 10)
    private String number;
    @Column(name = "city", length = 100)
    private String city;
    @Column(name = "state", length = 50)
    private String state;
    @Column(name = "country", length = 100)
    private String country;

    @CreatedBy
    @Column(name = "created_by", columnDefinition = "varchar(200)")
    private String createdBy;

    @CreatedDate
    @Column(name = "created_at", columnDefinition = "timestamp")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "last_modified_at", columnDefinition = "timestamp")
    private LocalDateTime lastModifiedAt;
    @Column(name = "last_modified_by")
    @LastModifiedBy
    private String lastModifiedBy;

    public Address(Address a) {
        setCity(a.getCity());
        setStreet(a.getStreet());
        setDistrict(a.getDistrict());
        setCountry(a.getCountry());
        setState(a.getState());
        setNumber(a.getNumber());
        setId(a.getId());
    }

    public Address(String street, String district, String number, String city, String state, String country){
        this.street = street;
        this.district = district;
        this.number = number;
        this.city = city;
        this.state = state;
        this.country = country;
    }
}