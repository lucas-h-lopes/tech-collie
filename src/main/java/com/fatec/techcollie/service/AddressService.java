package com.fatec.techcollie.service;

import com.fatec.techcollie.model.Address;
import com.fatec.techcollie.model.User;
import com.fatec.techcollie.repository.AddressRepository;
import com.fatec.techcollie.service.exception.InternalServerErrorException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;

    public AddressService(AddressRepository addressRepository, UserService userService) {
        this.addressRepository = addressRepository;
        this.userService = userService;
    }

    @Transactional
    public Address insert(Address address) {
        try {
            return addressRepository.save(address);
        } catch (Exception e) {
            throw new InternalServerErrorException("Algo deu errado durante o processamento da solicitação");
        }
    }

    @Transactional
    public Address update(Integer userId, Address address) {
        User user = userService.getById(userId);

        updateNonNull(user, address);

        return user.getAddress();
    }

    private void updateNonNull(User user, Address newAddress) {
        if (user.getAddress() == null) {
            user.setAddress(newAddress);
            return;
        }
        Address uAddress = user.getAddress();
        if (uAddress.getCity() == null || newAddress.getCity() != null) {
            uAddress.setCity(newAddress.getCity());
        }
        if (uAddress.getState() == null || newAddress.getState() != null) {
            uAddress.setState(newAddress.getState());
        }
        if (uAddress.getCountry() == null || newAddress.getCountry() != null) {
            uAddress.setCountry(newAddress.getCountry());
        }

        if (uAddress.getStreet() == null || newAddress.getStreet() != null) {
            uAddress.setStreet(newAddress.getStreet());
        }
        if (uAddress.getDistrict() == null || newAddress.getDistrict() != null) {
            uAddress.setDistrict(newAddress.getDistrict());
        }
        if (uAddress.getNumber() == null || newAddress.getNumber() != null) {
            uAddress.setNumber(newAddress.getNumber());
        }
    }
}
