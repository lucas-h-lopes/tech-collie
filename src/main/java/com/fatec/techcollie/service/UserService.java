package com.fatec.techcollie.service;

import com.fatec.techcollie.model.User;
import com.fatec.techcollie.repository.UserRepository;
import com.fatec.techcollie.service.exception.UniqueViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            user.setUsername("@".concat(user.getUsername()));
            return userRepository.save(user);
        }catch(DataIntegrityViolationException e){
            log.info("Error: ", e);
            throw new UniqueViolationException("Username or email has already been taken");
        }
    }
}
