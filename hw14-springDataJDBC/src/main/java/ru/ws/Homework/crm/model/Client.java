package ru.ws.Homework.crm.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

@Table("client")
public class Client implements Cloneable {

    @Id
    @Nonnull
    private Long id;

    @Nonnull
    private String name;

    @MappedCollection(idColumn = "id")
    private Address address;

    @MappedCollection(idColumn = "client_id")
    private Set<Phone> phones;

    public Client() {
    }

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(String name, Address address) {
        this.id = null;
        this.name = name;
        this.address = address;
    }

    public Client(String name, Address address, Set<Phone> phones) {
        this.id = null;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
        this.address = null;
        this.phones = null;
    }

    public Client(Long id, String name, Address address, Set<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    @PersistenceConstructor
    private Client(Long id, String name, Address address) {
        this(id, name, address, new HashSet<>());
    }

    @Override
    public Client clone() {
        Client clientCopy = new Client(this.id, this.name, this.address);
        return clientCopy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    public void addPhone(Phone phone) {
        phones.add(phone);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phones='" + phones + '\'' +
                '}';
    }

}