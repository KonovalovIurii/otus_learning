package ru.ws.Homework.crm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ws.Homework.crm.model.Address;


public interface AddressRepository extends CrudRepository<Address, String> {
}