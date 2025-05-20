package com.fatec.techcollie.repository;

import com.fatec.techcollie.model.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, Integer> {

}
