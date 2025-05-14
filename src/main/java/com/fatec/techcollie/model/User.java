package com.fatec.techcollie.model;

import com.fatec.techcollie.model.enums.Seniority;
import com.fatec.techcollie.model.enums.UserRole;
import com.fatec.techcollie.web.dto.user.UserCreateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

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
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "address_id")
    private Address address;
    @Column(name = "seniority")
    @Enumerated(EnumType.STRING)
    private Seniority seniority;
    @Column(name = "profile_url")
    private String profilePicUrl;
    @Column(name = "interest_area")
    private String interestArea;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role = UserRole.MINIMUM_ACCESS;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

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

    public User(UserCreateDTO dto) {
        this.name = dto.name();
        this.surname = dto.surname();
        this.password = dto.password();
        this.email = dto.email();
        this.username = dto.username();
    }

    public User(String username, String name, String surname, String email, String password, LocalDate birthDate, Seniority seniority, String profilePicUrl, String interestArea, UserRole role, Address address) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.seniority = seniority;
        this.profilePicUrl = profilePicUrl;
        this.interestArea = interestArea;
        this.role = role;
        this.address = address;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(surname, user.surname) && Objects.equals(birthDate, user.birthDate) && Objects.equals(address, user.address) && seniority == user.seniority && Objects.equals(profilePicUrl, user.profilePicUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, name, email, password, interestArea, surname, birthDate, address, seniority, profilePicUrl);
    }
}