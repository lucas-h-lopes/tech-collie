package com.fatec.techcollie.service;

import com.fatec.techcollie.builder.AuditingLogRequestBuilder;
import com.fatec.techcollie.jwt.AuthenticatedUserProvider;
import com.fatec.techcollie.logging.LogService;
import com.fatec.techcollie.model.User;
import com.fatec.techcollie.model.enums.Action;
import com.fatec.techcollie.repository.UserRepository;
import com.fatec.techcollie.service.exception.BadRequestException;
import com.fatec.techcollie.service.exception.InternalServerErrorException;
import com.fatec.techcollie.service.exception.NotFoundException;
import com.fatec.techcollie.service.exception.UniqueViolationException;
import com.fatec.techcollie.web.dto.user.UserSummaryDTO;
import com.fatec.techcollie.web.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
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
    private final LogService logService;
    private final AuditingLogRequestBuilder builder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, LogService logService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.logService = logService;
        this.builder = new AuditingLogRequestBuilder();
    }

    @Transactional
    public User insert(User user) {
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new UniqueViolationException("Email já cadastrado no sistema");
        }

        if (userRepository.existsByUsernameIgnoreCase(user.getUsername())) {
            throw new UniqueViolationException("Nome de usuário já cadastrado no sistema");
        }

        try {
            user.setName(formatName(user.getName()));
            user.setSurname(formatName(user.getSurname()));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);

            String authenticatedEmail = AuthenticatedUserProvider.getAuthenticatedEmail();

            logService.insertIntoLog(
                    builder.withAction(Action.INSERT)
                            .withEmail(authenticatedEmail)
                            .withRecordId(savedUser.getId().toString())
                            .withTableName(User.class)
                            .build()
            );
            return savedUser;
        } catch (Exception e) {
            throw new InternalServerErrorException("Algo deu errado durante o processamento da solicitação");
        }
    }

    //@Transactional(readOnly = true)
    public User getById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }

    @Transactional(readOnly = true)
    public Page<UserSummaryDTO> findAll(Pageable pageable, String firstName, String surname, String username) {
        User user = new User();
        user.setRole(null);
        user.setName(firstName);
        user.setSurname(surname);
        user.setUsername(username);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<User> example = Example.of(user, matcher);

        Page<User> projections = userRepository.findAll(example, pageable);

        return projections.map(UserMapper::toUserSummaryDto);
    }

    @Transactional
    public void deleteById(Integer oldId) {
        getById(oldId);
        try {
            userRepository.deleteById(oldId);

            String authenticatedEmail = AuthenticatedUserProvider.getAuthenticatedEmail();

            logService.insertIntoLog(
                    builder.withAction(Action.DELETE)
                            .withEmail(authenticatedEmail)
                            .withRecordId(oldId.toString())
                            .withTableName(User.class)
                            .build()
            );

        } catch (Exception e) {
            throw new InternalServerErrorException("Algo deu errado durante o processamento da requisição");
        }
    }

    @Transactional
    public void updateAdditional(User additionalUser) {
        User user = getById(AuthenticatedUserProvider.getAuthenticatedId());

        logService.insertIntoLog(
                builder.withAction(Action.UPDATE)
                        .withTableName(User.class)
                        .withEmail(user.getEmail())
                        .withRecordId(user.getId().toString())
                        .build()
        );

        formatNonNull(user, additionalUser);
    }

    @Transactional
    public void updatePassword(String currentPassword, String newPassword, String confirmationPassword) {
        if (!newPassword.equals(confirmationPassword)) {
            throw new BadRequestException("A nova senha e confirmação de senha devem ser iguais");
        }

        User user = getById(AuthenticatedUserProvider.getAuthenticatedId());

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BadRequestException("As senhas não conferem");
        }

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new BadRequestException("A nova senha precisa ser diferente da atual");
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        logService.insertIntoLog(
                builder.withAction(Action.UPDATE)
                        .withTableName(User.class)
                        .withEmail(user.getEmail())
                        .withRecordId(user.getId().toString())
                        .build()
        );
    }

    public Object getUserBasedOnIdentifier(String identifier) {
        try {
            int intUserId = Integer.parseInt(identifier);
            int authenticatedId = AuthenticatedUserProvider.getAuthenticatedId();
            User user = getById(intUserId);

            if (authenticatedId == intUserId) {
                return UserMapper.toUserDetailsDto(user);
            }
            return UserMapper.toUserSummaryDto(user);
        } catch (NumberFormatException e) {
            User user = getByUsername(identifier);
            String authenticatedEmail = AuthenticatedUserProvider.getAuthenticatedUsername();

            if (identifier.equalsIgnoreCase(authenticatedEmail)) {
                return UserMapper.toUserDetailsDto(user);
            }
            return UserMapper.toUserSummaryDto(user);
        }
    }

    public User getByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
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

        if (newUser.getName() != null && !newUser.getName().isBlank()) {
            user.setName(formatName(newUser.getName()));
        }
        if (newUser.getSurname() != null && !newUser.getSurname().isBlank()) {
            user.setSurname(formatName(newUser.getSurname()));
        }
    }

    private String formatName(String name) {
        return Arrays.stream(name.trim()
                        .replaceAll("\\s+", " ")
                        .split(" "))
                .map(x -> x.substring(0, 1).toUpperCase() + x.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));

    }
}
