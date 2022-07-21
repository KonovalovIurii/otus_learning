package com.datasrc.crm.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.datasrc.crm.model.Client;

import java.util.List;
import java.util.Optional;


public interface ClientRepository extends CrudRepository<Client, Long> {


    Optional<Client> findByName(String name);

    @Modifying
    @Query("update client set name = :newName where id = :id")
    void updateName(@Param("id") long id, @Param("newName") String newName);

    @Override
    @Query(value = """
            select c.id as client_id,
                   c.name as client_name,
                   a.id as address_id,
                   a.street as street,
                   p.id as phone_id,
                   p.number as phone_number
            from client c
            left outer join address a on a.id = c.id
            left outer join phone p on p.client_id = c.id
            order by c.id, p.id
                                                          """,
            resultSetExtractorClass = ClientResultSetExtractorClass.class)
    List<Client> findAll();
}