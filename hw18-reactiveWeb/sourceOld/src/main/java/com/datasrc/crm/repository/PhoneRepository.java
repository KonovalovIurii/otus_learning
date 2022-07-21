package com.datasrc.crm.repository;

import org.springframework.data.repository.CrudRepository;
import com.datasrc.crm.model.Phone;


public interface PhoneRepository extends CrudRepository<Phone, Long> {
}