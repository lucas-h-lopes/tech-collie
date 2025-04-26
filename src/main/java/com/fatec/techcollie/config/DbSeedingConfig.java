package com.fatec.techcollie.config;

import com.fatec.techcollie.model.Address;
import com.fatec.techcollie.model.User;
import com.fatec.techcollie.model.enums.Seniority;
import com.fatec.techcollie.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
@Profile("dev")
public class DbSeedingConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public DbSeedingConfig(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @PostConstruct
    public void init() {
        if (userRepository.findByEmail("default@email.com").isEmpty()) {
            User user = new User();
            Address address = new Address();
            address.setStreet("Rua Emilio Leao Brambila");
            address.setDistrict("Centro");
            address.setNumber("46");
            address.setCity("Sumare");
            address.setState("Sao Paulo");
            address.setCountry("Brasil");

            user.setName("Default");
            user.setSurname("User");
            user.setSeniority(Seniority.SPECIALIST);
            user.setBirthDate(LocalDate.of(2004, 11, 19));
            user.setUsername("default");
            user.setInterestArea("Backend");
            user.setProfilePicUrl("https://driver/picture.png");
            user.setEmail("default@email.com");
            user.setPassword(encoder.encode("123456aA@"));
            user.setAddress(address);

            fillBasicFields(user);
            userRepository.save(user);
        }
    }

    private void fillBasicFields(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("system");
        user.setLastModifiedAt(LocalDateTime.now());
        user.setLastModifiedBy("system");
    }
}
