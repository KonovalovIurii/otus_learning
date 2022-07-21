package com.datasrc.crm.repository;

import org.springframework.data.repository.CrudRepository;
import com.datasrc.crm.model.Address;


public interface AddressRepository extends CrudRepository<Address, String> {
}