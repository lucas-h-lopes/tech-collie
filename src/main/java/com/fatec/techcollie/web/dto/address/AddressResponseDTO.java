package com.fatec.techcollie.web.dto.address;

import com.fatec.techcollie.model.Address;

public record AddressResponseDTO(
        Integer id,
        String street,
        String district,
        String number,
        String city,
        String state,
        String country
) {

    public AddressResponseDTO(Address address) {
        this(address.getId(),
                address.getStreet(),
                address.getDistrict(),
                address.getNumber(),
                address.getCity(),
                address.getState(),
                address.getCountry());
    }
}
