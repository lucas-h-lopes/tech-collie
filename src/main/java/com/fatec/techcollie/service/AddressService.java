package com.fatec.techcollie.service;

import com.fatec.techcollie.builder.AuditingLogRequestBuilder;
import com.fatec.techcollie.jwt.AuthenticatedUserProvider;
import com.fatec.techcollie.logging.LogService;
import com.fatec.techcollie.model.Address;
import com.fatec.techcollie.model.User;
import com.fatec.techcollie.model.enums.Action;
import com.fatec.techcollie.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;
    private final LogService logService;
    private final AuditingLogRequestBuilder builder;
    private final AuthenticatedUserProvider provider;

    public AddressService(AddressRepository addressRepository, UserService userService, LogService logService, AuthenticatedUserProvider provider) {
        this.addressRepository = addressRepository;
        this.userService = userService;
        this.logService = logService;
        this.builder = new AuditingLogRequestBuilder();
        this.provider = provider;
    }

    @Transactional
    public Address update(Address address) {
        User user = userService.getById(provider.getAuthenticatedId());

        updateNonNull(user, address);

        return user.getAddress();
    }

    private void updateNonNull(User user, Address newAddress) {
        if (user.getAddress() == null) {
            user.setAddress(newAddress);

            addressRepository.save(user.getAddress());

            String authenticatedEmail = provider.getAuthenticatedEmail();

            logService.insertIntoLog(
                    builder.withEmail(authenticatedEmail)
                            .withTableName(Address.class)
                            .withRecordId(user.getAddress().getId())
                            .withAction(Action.INSERT)
                            .build()
            );
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


        String authenticatedEmail = provider.getAuthenticatedEmail();

        logService.insertIntoLog(
                builder
                        .withAction(Action.UPDATE)
                        .withRecordId(user.getAddress().getId())
                        .withEmail(authenticatedEmail)
                        .withTableName(Address.class)
                        .build()
        );
    }
}
