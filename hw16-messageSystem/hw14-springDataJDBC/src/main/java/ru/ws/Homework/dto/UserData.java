package ru.ws.Homework.dto;

import ru.otus.messagesystem.client.ResultDataType;
import ru.ws.Homework.db.crm.model.Client;

public class UserData implements ResultDataType {

    private final Client client;

    public UserData( Client client) {
        this.client = client;
    }


    public Client  getData() {
        return client;
    }

    @Override
    public String toString() {
        return "UserData{" +
                ", data='" + client.toString() + '\'' +
                '}';
    }
}
