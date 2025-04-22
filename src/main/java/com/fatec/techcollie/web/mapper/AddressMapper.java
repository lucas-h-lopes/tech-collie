package com.fatec.techcollie.web.mapper;

import com.fatec.techcollie.model.Address;
import com.fatec.techcollie.web.dto.address.AddressDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressMapper {

    public static Address toAddress(AddressDTO dto){
        Address address = new Address();
        address.setCity(dto.city());
        address.setCountry(dto.country());
        address.setState(dto.state());
        return address;
    }
}
