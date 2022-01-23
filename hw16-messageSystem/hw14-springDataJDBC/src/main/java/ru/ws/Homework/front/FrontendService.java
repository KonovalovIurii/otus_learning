package ru.ws.Homework.front;

import ru.ws.Homework.dto.UserData;
import ru.otus.messagesystem.client.MessageCallback;

public interface FrontendService {
    void getClientData(UserData clienData, MessageCallback<UserData> dataConsumer);
}

