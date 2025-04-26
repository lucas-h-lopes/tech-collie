package com.fatec.techcollie.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor @EqualsAndHashCode
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
}