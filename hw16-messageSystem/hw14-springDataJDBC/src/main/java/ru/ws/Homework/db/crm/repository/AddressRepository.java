package ru.ws.Homework.db.crm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ws.Homework.db.crm.model.Address;


public interface AddressRepository extends CrudRepository<Address, String> {
}