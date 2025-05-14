package com.fatec.techcollie.repository.projection.impl;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fatec.techcollie.model.Address;
import com.fatec.techcollie.model.User;
import com.fatec.techcollie.repository.projection.UserProjection;

import java.time.LocalDate;
@JsonPropertyOrder({"id", "username", "name", "surname", "email", "birthDate", "seniority", "interestArea", "profilePictureUrl", "address"})
public class UserProjectionImplementation implements UserProjection {

    private final Integer id;
    private final String username;
    private final String name;
    private final String surname;
    private final String email;
    private final LocalDate birthDate;
    private final Address address;
    private String seniority = null;
    private final String profilePictureUrl;
    private final String interestArea;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSurname() {
        return this.surname;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    @Override
    public Address getAddress() {
        return this.address;
    }

    @Override
    public String getSeniority() {
        return this.seniority;
    }

    @Override
    public String getProfilePictureUrl() {
        return this.profilePictureUrl;
    }

    @Override
    public String getInterestArea() {
        return this.interestArea;
    }

    public UserProjectionImplementation(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.birthDate = user.getBirthDate();
        this.address = user.getAddress();
        if (user.getSeniority() != null) {
            this.seniority = user.getSeniority().name();
        }
        this.profilePictureUrl = user.getProfilePicUrl();
        this.interestArea = user.getInterestArea();
    }
}
