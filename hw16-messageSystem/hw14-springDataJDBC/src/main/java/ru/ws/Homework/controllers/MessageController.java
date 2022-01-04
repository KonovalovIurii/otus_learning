package ru.ws.Homework.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystemImpl;
import com.google.gson.Gson;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;
import ru.ws.Homework.Message;
import ru.ws.Homework.db.crm.model.Address;
import ru.ws.Homework.db.crm.model.Client;
import ru.ws.Homework.db.crm.model.Phone;
import ru.ws.Homework.db.crm.service.DBServiceClient;
import ru.ws.Homework.db.handlers.GetUserDataRequestHandler;
import ru.ws.Homework.dto.UserData;
import ru.ws.Homework.front.FrontendServiceImpl;
import ru.ws.Homework.front.handlers.GetUserDataResponseHandler;
import javax.json.*;
import java.io.StringReader;
import java.util.Set;


@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    private final DBServiceClient dbServiceClient;
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";
    private final MessageSystemImpl messageSystem;
    private final HandlersStoreImpl requestHandlerDatabaseStore;
    private final MsClientImpl databaseMsClient;
    private final HandlersStoreImpl requestHandlerFrontendStore;
    private final MsClientImpl frontendMsClient;

    private FrontendServiceImpl frontendService;

    public MessageController(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
        this.messageSystem = new MessageSystemImpl();

        this.requestHandlerDatabaseStore  = new HandlersStoreImpl();
        requestHandlerDatabaseStore.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(dbServiceClient));
        this.databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem, requestHandlerDatabaseStore);
        messageSystem.addClient(databaseMsClient);

        this.requestHandlerFrontendStore = new HandlersStoreImpl();
        requestHandlerFrontendStore.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler());
        this.frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem, requestHandlerFrontendStore);
        messageSystem.addClient(frontendMsClient);

        frontendService = new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
    }


    @MessageMapping("/message")
    @SendTo("/topic/response")
    public Message getMessage(String clientWeb) {
        //добавляем клиента в сообщение и отправляем его

        JsonReader jsonReader = Json.createReader( new StringReader(clientWeb));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();
        var client = new Client(object.getString("name"), new Address(object.getString("address")), Set.of(new Phone(object.getString("phone"))));

        frontendService.getUserData(new UserData(client), data -> logger.info("got data:{}", data));

        // вернем на страницу всех клиентов
        String allClient = new Gson().toJson(dbServiceClient.findAll());
        return new Message(HtmlUtils.htmlEscape(allClient));
    }

}
