package com.fatec.techcollie.repository.projection;

import com.fatec.techcollie.model.Address;

import java.time.LocalDate;

public interface UserProjection {

    Integer getId();
    String getUsername();
    String getName();
    String getSurname();
    String getEmail();
    LocalDate getBirthDate();
    Address getAddress();
    String getSeniority();
    String getProfilePictureUrl();
    String getInterestArea();
}
