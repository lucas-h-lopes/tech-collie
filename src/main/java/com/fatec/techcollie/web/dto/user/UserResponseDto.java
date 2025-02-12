package com.fatec.techcollie.web.dto.user;

import com.fatec.techcollie.model.Address;
import com.fatec.techcollie.model.User;
import com.fatec.techcollie.model.enums.Seniority;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class UserResponseDto {

    private Integer id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private LocalDate birthdate;
    private Address address;
    private Seniority seniority;
    private String areaInterest;
    private String profileUrl;

    public UserResponseDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.birthdate = user.getBirthdate();
        this.address = user.getAddress();
        this.seniority = user.getSeniority();
        this.areaInterest = user.getAreaInterest();
        this.profileUrl = user.getProfilePicUrl();
    }
}
