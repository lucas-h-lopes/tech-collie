package com.fatec.techcollie.model;

import com.fatec.techcollie.model.enums.Seniority;
import com.fatec.techcollie.web.dto.user.UserCreateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User implements Serializable {

    public User(UserCreateDto dto){
        this.name = dto.name();
        this.surname = dto.surname();
        this.password = dto.password();
        this.email = dto.email();
        this.username = dto.username();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "username", unique = true, length = 15, nullable = false)
    private String username;
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @Column(name = "email", length = 200, nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "surname", nullable = false, length = 50)
    private String surname;
    @Column(name = "birthdate")
    private LocalDate birthdate;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @Column(name = "seniority")
    @Enumerated(EnumType.STRING)
    private Seniority seniority;
    @Column(name = "profile_url")
    private String profilePicUrl;
    @Column(name = "area_interest")
    private String areaInterest;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(surname, user.surname) && Objects.equals(birthdate, user.birthdate) && Objects.equals(address, user.address) && seniority == user.seniority && Objects.equals(profilePicUrl, user.profilePicUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, name, email, password,areaInterest, surname, birthdate, address, seniority, profilePicUrl);
    }
}
