package com.fatec.techcollie.service;

import com.fatec.techcollie.model.User;
import com.fatec.techcollie.repository.UserRepository;
import com.fatec.techcollie.repository.projection.UserProjection;
import com.fatec.techcollie.service.exception.BadRequestException;
import com.fatec.techcollie.service.exception.InternalServerErrorException;
import com.fatec.techcollie.service.exception.NotFoundException;
import com.fatec.techcollie.service.exception.UniqueViolationException;
import com.fatec.techcollie.web.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User insert(User user) {
        try {
            user.setName(formatName(user.getName()));
            user.setSurname(formatSurname(user.getSurname()));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueViolationException("Nome de usuário ou email já cadastrados no sistema");
        } catch (Exception e){
            throw new InternalServerErrorException("Algo deu errado durante o processamento da solicitação");
        }
    }

    //@Transactional(readOnly = true)
    public User getById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }

    @Transactional(readOnly = true)
    public Page<UserProjection> findAll(Pageable pageable, String firstName, String surname, String username) {
        User user = new User();
        user.setName(firstName);
        user.setSurname(surname);
        user.setUsername(username);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<User> example = Example.of(user, matcher);

        Page<User> projections = userRepository.findAll(example, pageable);
        return projections.map(UserMapper::toUserProjection);
    }

    @Transactional
    public void deleteById(Integer id) {
        User user = getById(id);
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new InternalServerErrorException("Algo deu errado durante o processamento da requisição");
        }
    }

    @Transactional
    public void updateAdditional(Integer userId, User additionalUser) {
        User user = getById(userId);

        formatNonNull(user, additionalUser);
    }

    @Transactional
    public void updatePassword(String currentPassword, String newPassword, String confirmationPassword, int userId){
        if(!newPassword.equals(confirmationPassword)){
            throw new BadRequestException("A nova senha e confirmação de senha devem ser iguais");
        }

        User user = getById(userId);

        if(!passwordEncoder.matches(currentPassword, user.getPassword())){
            throw new BadRequestException("As senhas não conferem");
        }

        if(passwordEncoder.matches(newPassword, user.getPassword())){
            throw new BadRequestException("A nova senha precisa ser diferente da atual");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
    }

    private void formatNonNull(User user, User newUser) {
        if (newUser.getSeniority() != null && !newUser.getSeniority().name().isBlank()) {
            user.setSeniority(newUser.getSeniority());
        }
        if (newUser.getBirthDate() != null) {
            user.setBirthDate(newUser.getBirthDate());
        }
        if (newUser.getProfilePicUrl() != null && !newUser.getProfilePicUrl().isBlank()) {
            user.setProfilePicUrl(newUser.getProfilePicUrl());
        }
        if (newUser.getInterestArea() != null && !newUser.getInterestArea().isBlank()) {
            user.setInterestArea(newUser.getInterestArea());
        }
    }

    private String formatName(String name) {
        return Arrays.stream(name.trim()
                        .replaceAll("\\s+", " ")
                        .split(" "))
                .map(x -> x.substring(0, 1).toUpperCase() + x.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));

    }

    private String formatSurname(String surname) {
        return Arrays.stream(surname.trim()
                        .replaceAll("\\s+", " ")
                        .split(" "))
                .map(x -> x.substring(0, 1).toUpperCase() + x.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

}
