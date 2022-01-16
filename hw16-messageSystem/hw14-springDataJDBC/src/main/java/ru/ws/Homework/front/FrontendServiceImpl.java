package ru.ws.Homework.front;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import ru.ws.Homework.dto.UserData;
import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.message.MessageType;

@Service
@ComponentScan("ru.otus.messagesystem")
public record FrontendServiceImpl(MsClient msClient,
                                  String databaseServiceClientName) implements FrontendService {

    @Override
    public void getUserData(UserData clientData, MessageCallback<UserData> dataConsumer) {
        var outMsg = msClient.produceMessage(databaseServiceClientName, clientData,
                MessageType.USER_DATA, dataConsumer);
        msClient.sendMessage(outMsg);
    }
}
