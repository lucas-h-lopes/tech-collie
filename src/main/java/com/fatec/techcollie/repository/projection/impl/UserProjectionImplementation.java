package com.fatec.techcollie.repository.projection.impl;

import com.fatec.techcollie.model.Address;
import com.fatec.techcollie.model.User;
import com.fatec.techcollie.repository.projection.UserProjection;

import java.time.LocalDate;

public class UserProjectionImplementation implements UserProjection {

    private final Integer id;
    private final String username;
    private final String name;
    private final String surname;
    private final String email;
    private final LocalDate birthdate;
    private final Address address;
    private String seniority = null;
    private final String profilePicUrl;
    private final String areaInterest;

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
    public LocalDate getBirthdate() {
        return this.birthdate;
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
    public String getProfilePicUrl() {
        return this.profilePicUrl;
    }

    @Override
    public String getAreaInterest() {
        return this.areaInterest;
    }

    public UserProjectionImplementation(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.birthdate = user.getBirthDate();
        this.address = user.getAddress();
        if (user.getSeniority() != null) {
            this.seniority = user.getSeniority().name();
        }
        this.profilePicUrl = user.getProfilePicUrl();
        this.areaInterest = user.getInterestArea();
    }
}
