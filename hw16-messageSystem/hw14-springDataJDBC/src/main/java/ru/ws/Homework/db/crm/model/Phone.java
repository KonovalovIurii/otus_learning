package ru.ws.Homework.db.crm.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table("phone")
public class Phone implements Cloneable {

    @Id
    private Long id;

    private String number;

    public Phone() {
    }

    public Phone(String number) {
        this(null, number);
    }
    @PersistenceConstructor
    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                //", client='" + client.getId() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone that = (Phone) o;
        return id == that.id &&
                number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }

}