package ru.ws.Homework.crm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ws.Homework.crm.model.Phone;


public interface PhoneRepository extends CrudRepository<Phone, Long> {
}