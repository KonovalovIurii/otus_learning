package ru.ws.Homework.db.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.ws.Homework.controllers.MessageController;
import ru.ws.Homework.db.crm.model.Client;
import ru.ws.Homework.db.crm.service.DBServiceClient;
import ru.ws.Homework.dto.UserData;

import java.util.Optional;


public class GetUserDataRequestHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(GetUserDataRequestHandler.class);
    private final DBServiceClient dbServiceClient;

    public GetUserDataRequestHandler(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    public <T extends ResultDataType> Optional<Message<T>> handle(Message<T> msg) {
        var clientData = (UserData) msg.getData();
        logger.info("DB service handler:{}", clientData);
        var UserData = new UserData(dbServiceClient.saveClient(clientData.getData()));
        return Optional.of(MessageBuilder.buildReplyMessage(msg, (T) UserData));
    }
}
