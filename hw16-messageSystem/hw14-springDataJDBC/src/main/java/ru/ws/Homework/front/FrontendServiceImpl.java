package ru.ws.Homework.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import ru.ws.Homework.dto.UserData;
import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.message.MessageType;


//@ComponentScan("ru.otus.messagesystem")
public class FrontendServiceImpl implements FrontendService {

    private  MsClient msClient;
    private  String databaseServiceClientName;

    public FrontendServiceImpl(MsClient msClient,
                               String databaseServiceClientName) {
         this.msClient = msClient;
         this.databaseServiceClientName = databaseServiceClientName;
    }

    @Override
    public void getClientData(UserData clientData, MessageCallback<UserData> dataConsumer) {
        var outMsg = msClient.produceMessage(databaseServiceClientName, clientData,
                MessageType.USER_DATA, dataConsumer);
        msClient.sendMessage(outMsg);
    }

}
