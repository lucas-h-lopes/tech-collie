package com.fatec.techcollie.repository.projection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fatec.techcollie.model.Address;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface UserProjection {

    Integer getId();
    String getUsername();
    String getName();
    String getSurname();
    String getEmail();
    LocalDate getBirthdate();
    Address getAddress();
    String getSeniority();
    String getProfilePicUrl();
    String getAreaInterest();
}
