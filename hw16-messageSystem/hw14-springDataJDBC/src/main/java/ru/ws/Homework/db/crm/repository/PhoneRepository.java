package ru.ws.Homework.db.crm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ws.Homework.db.crm.model.Phone;


public interface PhoneRepository extends CrudRepository<Phone, Long> {
}