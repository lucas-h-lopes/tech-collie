package com.fatec.techcollie.service;

import com.fatec.techcollie.model.User;
import com.fatec.techcollie.repository.UserRepository;
import com.fatec.techcollie.repository.projection.UserProjection;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public User insert(User user){
        try{
            user.setName(formatName(user.getName()));
            user.setSurname(formatSurname(user.getSurname()));
            return userRepository.save(user);
        }catch(DataIntegrityViolationException e){
            log.info("Error: ", e);
            throw new UniqueViolationException("Nome de usuário ou email já cadastrados no sistema");
        }
    }

    @Transactional(readOnly = true)
    public User getById(Integer id){
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }

    @Transactional(readOnly = true)
    public Page<UserProjection> findAll(Pageable pageable, String firstName, String surname, String username){
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
    public void deleteById(Integer id){
            User user = getById(id);
        try {
            userRepository.deleteById(id);
        }catch(Exception e){
            log.info("Algo deu errado durante a exclusão do usuário");
            throw new InternalServerErrorException("Algo deu errado durante o processamento da requisição");
        }
    }

    private String formatName(String name){
        return Arrays.stream(name.trim()
                .replaceAll("\\s+", " ")
                .split(" "))
                .map(x -> x.substring(0,1).toUpperCase() + x.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));

    }

    private String formatSurname(String surname){
        return Arrays.stream(surname.trim()
                .replaceAll("\\s+", " ")
                .split(" "))
                .map(x -> x.substring(0,1).toUpperCase() + x.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
